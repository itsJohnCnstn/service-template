package com.johncnstn.servicetemplate.controller.api;

import com.johncnstn.servicetemplate.model.UnexpectedError;
import com.johncnstn.servicetemplate.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@Api(value = "Users")
public interface UserApi {

    String createUserPath = "/api/v1/users";
    String deleteUserPath = "/api/v1/users/{id}";
    String getUserPath = "/api/v1/users/{id}";
    String listUsersPath = "/api/v1/users";
    String updateUserPath = "/api/v1/users/{id}";

    @ApiOperation(
            value = "Create a user",
            nickname = "createUser",
            notes = "",
            response = User.class,
            tags = {
                "users",
            })
    @ApiResponses(
            value = {
                @ApiResponse(code = 201, message = "A paged array of pets", response = User.class),
                @ApiResponse(
                        code = 200,
                        message = "unexpected error",
                        response = UnexpectedError.class)
            })
    @RequestMapping(
            value = createUserPath,
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<User> createUser(
            @ApiParam(value = "", required = true) @Valid @RequestBody User user);

    @ApiOperation(
            value = "",
            nickname = "deleteUser",
            notes = "",
            response = User.class,
            tags = {
                "users",
            })
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "OK", response = User.class),
                @ApiResponse(code = 200, message = "")
            })
    @RequestMapping(
            value = deleteUserPath,
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<User> deleteUser(
            @ApiParam(value = "The id of the user", required = true) @PathVariable("id") UUID id);

    @ApiOperation(
            value = "Info for a specific user",
            nickname = "getUser",
            notes = "",
            response = User.class,
            tags = {
                "users",
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        code = 200,
                        message = "Expected response to a valid request",
                        response = User.class),
                @ApiResponse(
                        code = 200,
                        message = "unexpected error",
                        response = UnexpectedError.class)
            })
    @RequestMapping(
            value = getUserPath,
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<User> getUser(
            @ApiParam(value = "The id of the user", required = true) @PathVariable("id") UUID id);

    @ApiOperation(
            value = "List all users",
            nickname = "listUsers",
            notes = "",
            response = User.class,
            responseContainer = "List",
            tags = {
                "users",
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        code = 200,
                        message = "A paged array of pets",
                        response = User.class,
                        responseContainer = "List"),
                @ApiResponse(
                        code = 200,
                        message = "unexpected error",
                        response = UnexpectedError.class)
            })
    @RequestMapping(
            value = listUsersPath,
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<User>> listUsers(
            @Min(1)
                    @ApiParam(value = "", defaultValue = "1")
                    @Valid
                    @RequestParam(value = "page", required = false, defaultValue = "1")
                    Integer page,
            @Min(1)
                    @Max(100)
                    @ApiParam(value = "", defaultValue = "30")
                    @Valid
                    @RequestParam(value = "size", required = false, defaultValue = "30")
                    Integer size);

    @ApiOperation(
            value = "",
            nickname = "updateUser",
            notes = "",
            response = User.class,
            tags = {
                "users",
            })
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "OK", response = User.class),
                @ApiResponse(code = 200, message = "")
            })
    @RequestMapping(
            value = updateUserPath,
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<User> updateUser(
            @ApiParam(value = "The id of the user", required = true) @PathVariable("id") UUID id,
            @ApiParam(value = "", required = true) @Valid @RequestBody User user);
}
