package io.github.javialc.java.labs.mongodb.mapper;

import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.model.UserRequest;
import io.github.javialc.java.labs.mongodb.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toEntity(UserRequest userEntity);

    @Mapping(target = "id", source = "id")
    UserEntity toEntity(String id, UserRequest userEntity);

    User toDomain(UserEntity userEntity);

    List<User> toDomain(List<UserEntity> userEntities);
}
