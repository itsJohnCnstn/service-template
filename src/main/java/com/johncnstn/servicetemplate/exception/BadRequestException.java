package com.johncnstn.servicetemplate.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestException extends AbstractThrowableProblem {

    public BadRequestException(final String message) {
        super(ErrorConstants.BAD_REQUEST, "Bad Request", Status.BAD_REQUEST, message);
    }
}
