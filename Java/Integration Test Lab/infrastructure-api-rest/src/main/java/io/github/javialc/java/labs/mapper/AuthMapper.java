package io.github.javialc.java.labs.mapper;

import io.github.javialc.java.labs.api.model.LoginDto;
import io.github.javialc.java.labs.api.model.UserRequestDto;
import io.github.javialc.java.labs.domain.model.LoginRequest;
import io.github.javialc.java.labs.domain.model.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    LoginRequest toLoginRequest(LoginDto loginDto);

    UserRequest toUserRequest(UserRequestDto userRequestDto);
}
