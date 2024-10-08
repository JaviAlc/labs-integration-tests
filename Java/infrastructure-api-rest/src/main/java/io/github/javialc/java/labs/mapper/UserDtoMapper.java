package io.github.javialc.java.labs.mapper;

import io.github.javialc.java.labs.api.model.UserDto;
import io.github.javialc.java.labs.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> users);
}
