package com.johncnstn.servicetemplate.exception;

import com.google.common.collect.ImmutableMap;
import com.johncnstn.servicetemplate.model.UnexpectedError;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling {

    private static final Map<String, Function<ServerErrorMessage, Violation>> PSQL_ERRORS =
            new ImmutableMap.Builder<String, Function<ServerErrorMessage, Violation>>()
                    .put(
                            "22P02", // invalid_text_representation
                            msg -> new Violation(msg.getDatatype(), "invalid enum value"))
                    .put(
                            "22003", // numeric_value_out_of_range
                            msg ->
                                    new Violation(
                                            msg.getColumn(),
                                            "numeric field overflow - " + msg.getDetail()))
                    .put(
                            "23502", // not_null_violation
                            msg ->
                                    new Violation(
                                            msg.getColumn(),
                                            "must not be null - " + msg.getConstraint()))
                    .put(
                            "23503", // foreign_key_violation
                            msg ->
                                    new Violation(
                                            msg.getColumn(),
                                            "referenced item is not present - " + msg.getDetail()))
                    .put(
                            "23505", // unique_violation
                            msg ->
                                    new Violation(
                                            msg.getConstraint(),
                                            "must be unique - " + msg.getDetail()))
                    .put(
                            "23514", // check_violation
                            msg -> new Violation(msg.getConstraint(), "constraint check failed"))
                    .build();

    @Override
    public ResponseEntity<Problem> process(
            @NotNull ResponseEntity<Problem> entity, NativeWebRequest request) {
        var problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }
        var problemBuilder =
                Problem.builder()
                        .withType(
                                Problem.DEFAULT_TYPE.equals(problem.getType())
                                        ? ErrorConstants.DEFAULT
                                        : problem.getType())
                        .withTitle(problem.getTitle())
                        .withStatus(problem.getStatus());

        if (problem instanceof ConstraintViolationProblem) {
            problemBuilder.withType(ErrorConstants.CONSTRAINT_VIOLATION);
            problemBuilder.with(
                    "violations", ((ConstraintViolationProblem) problem).getViolations());
        } else {
            problemBuilder
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance())
                    .withCause(((DefaultProblem) problem).getCause());
            problem.getParameters().forEach(problemBuilder::with);
        }
        return new ResponseEntity<>(
                problemBuilder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handle(
            DataIntegrityViolationException ex, NativeWebRequest request) {
        Violation violation = null;
        var rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof PSQLException psqlException) {
            if (PSQL_ERRORS.containsKey(psqlException.getSQLState())) {
                violation =
                        PSQL_ERRORS
                                .get(psqlException.getSQLState())
                                .apply(psqlException.getServerErrorMessage());
            }
        }
        if (violation == null) {
            var message = ExceptionUtils.getRootCauseMessage(ex);
            violation = new Violation("unknown", message);
        }
        var violations = List.of(violation);
        return newConstraintViolationProblem(ex, violations, request);
    }

    // TODO refactor with problem handling
    @ExceptionHandler(value = NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody UnexpectedError handleNoResourceFoundException(
            NoResourceFoundException ex) {
        return new UnexpectedError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody UnexpectedError handleNotFoundException(NotFoundException ex) {
        return new UnexpectedError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
