package io.github.javialc.java.labs.controller;

import io.github.javialc.java.labs.api.UserApi;
import io.github.javialc.java.labs.api.model.UserDto;
import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.usecase.FindUsersUseCase;
import io.github.javialc.java.labs.domain.utils.RequestSource;
import io.github.javialc.java.labs.domain.utils.RequestSourceContextHolder;
import io.github.javialc.java.labs.mapper.UserDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {

    private final FindUsersUseCase findUsersUseCase;
    private final UserDtoMapper userDtoMapper;

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {

        RequestSource requestSource = new RequestSource();
        requestSource.setEmail("email");
        requestSource.setJwtToken("jwtToken");

        RequestSourceContextHolder.setRequestSource(requestSource);

        final List<User> users = findUsersUseCase.findUsers();
        return ResponseEntity.ok(userDtoMapper.toUserDtoList(users));
    }
}
