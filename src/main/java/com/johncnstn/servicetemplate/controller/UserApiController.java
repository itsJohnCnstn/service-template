package com.johncnstn.servicetemplate.controller;

import static com.johncnstn.servicetemplate.util.HeaderUtils.generateLocationHeader;
import static com.johncnstn.servicetemplate.util.HeaderUtils.generatePaginationHeaders;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.johncnstn.servicetemplate.controller.api.UserApi;
import com.johncnstn.servicetemplate.model.User;
import com.johncnstn.servicetemplate.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserApiController implements UserApi {

    private final UserService userService;

    public ResponseEntity<User> createUser(@Valid User user) {
        var createdUser = userService.create(user);
        var header = generateLocationHeader(getUserPath, createdUser.getId());
        return new ResponseEntity<>(createdUser, header, CREATED);
    }

    public ResponseEntity<User> deleteUser(UUID id) {
        userService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    public ResponseEntity<User> getUser(UUID id) {
        var user = userService.get(id);
        return new ResponseEntity<>(user, OK);
    }

    public ResponseEntity<List<User>> listUsers(
            @Min(1) @Valid Integer page, @Min(1) @Max(100) @Valid Integer size) {
        var usersPage = userService.getAll(page, size);
        var headers = generatePaginationHeaders(usersPage, listUsersPath);
        return new ResponseEntity<>(usersPage.getContent(), headers, OK);
    }

    public ResponseEntity<User> updateUser(UUID id, @Valid User user) {
        var updatedUser = userService.update(id, user);
        return new ResponseEntity<>(updatedUser, OK);
    }
}
