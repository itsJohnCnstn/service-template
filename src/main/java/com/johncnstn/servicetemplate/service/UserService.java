package com.johncnstn.servicetemplate.service;

import com.johncnstn.servicetemplate.model.User;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface UserService {
    User create(User user);

    void delete(UUID id);

    User get(UUID id);

    Page<User> getAll(Integer page, Integer size);

    User update(UUID id, User userRequest);
}
