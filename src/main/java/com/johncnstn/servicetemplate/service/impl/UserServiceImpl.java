package com.johncnstn.servicetemplate.service.impl;

import static com.johncnstn.servicetemplate.exception.ExceptionUtils.entityNotFound;
import static com.johncnstn.servicetemplate.mapper.UserMapper.USER_MAPPER;
import static com.johncnstn.servicetemplate.util.PagingUtils.toPageable;

import com.johncnstn.servicetemplate.model.User;
import com.johncnstn.servicetemplate.repository.UserRepository;
import com.johncnstn.servicetemplate.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User create(User user) {
        var userToCreate = USER_MAPPER.toEntity(user);

        // TODO add check on email constraints violation

        var savedUser = userRepository.saveAndFlush(userToCreate);
        return USER_MAPPER.toModel(savedUser);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        userRepository
                .findOneByIdAndDeletedAtIsNull(id)
                .ifPresent(
                        existingUser -> {
                            log.debug("Deleting user with id {} ", id);
                            userRepository.softDeleteById(id);
                        });
    }

    @Override
    @Transactional(readOnly = true)
    public User get(UUID id) {
        return userRepository
                .findOneByIdAndDeletedAtIsNull(id)
                .map(
                        existingUser -> {
                            log.debug("Getting user with id {} ", id);
                            return USER_MAPPER.toModel(existingUser);
                        })
                .orElseThrow(entityNotFound(String.format("User with id %s was not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getAll(Integer page, Integer size) {
        var pageable = toPageable(page, size);
        return userRepository.findAllNotDeleted(pageable).map(USER_MAPPER::toModel);
    }

    @Override
    @Transactional
    public User update(UUID id, User userRequest) {
        return userRepository
                .findOneByIdAndDeletedAtIsNull(id)
                .map(
                        existingUser -> {
                            log.debug("Updating user with id {} ", id);
                            return USER_MAPPER.updateEntity(userRequest, existingUser);
                        })
                .map(userRepository::saveAndFlush)
                .map(USER_MAPPER::toModel)
                .orElseThrow(entityNotFound(String.format("User with id %s was not found", id)));
    }
}
