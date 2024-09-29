package io.github.javialc.java.labs.controller;

import io.github.javialc.java.labs.api.AuthApi;
import io.github.javialc.java.labs.api.model.LoginDto;
import io.github.javialc.java.labs.api.model.LoginResponseDto;
import io.github.javialc.java.labs.api.model.UserRequestDto;
import io.github.javialc.java.labs.domain.model.LoginRequest;
import io.github.javialc.java.labs.domain.model.LoginResponse;
import io.github.javialc.java.labs.domain.model.UserRequest;
import io.github.javialc.java.labs.domain.usecase.CreateNewUserUseCase;
import io.github.javialc.java.labs.domain.usecase.LoginUseCase;
import io.github.javialc.java.labs.mapper.AuthMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthController implements AuthApi {


    private final CreateNewUserUseCase createNewUserUseCase;
    private final LoginUseCase loginUseCase;
    private AuthMapper authMapper;


    @Override
    public ResponseEntity<LoginResponseDto> login(LoginDto loginDto) {
        LoginRequest loginRequest = authMapper.toLoginRequest(loginDto);
        LoginResponse loginResponse = loginUseCase.login(loginRequest);
        log.info("Login response: {}", loginResponse);
        return ResponseEntity.ok(authMapper.toLoginResponseDto(loginResponse));
    }

    @Override
    public ResponseEntity<Void> registerUser(UserRequestDto userRequestDto) {
        UserRequest userRequest = authMapper.toUserRequest(userRequestDto);
        createNewUserUseCase.createUser(userRequest);
        return ResponseEntity.noContent().build();
    }
}
