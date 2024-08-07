package com.johncnstn.servicetemplate.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.johncnstn.servicetemplate.entity.UserEntity;
import com.johncnstn.servicetemplate.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = getMapper(UserMapper.class);

    UserEntity toEntity(User user);

    User toModel(UserEntity user);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity updateEntity(User userRequest, @MappingTarget UserEntity existingUser);
}
