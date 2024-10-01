package io.github.javialc.java.labs.controller;

import io.github.javialc.java.labs.api.UserApi;
import io.github.javialc.java.labs.api.model.UserDto;
import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.usecase.users.DeleteUserUseCase;
import io.github.javialc.java.labs.domain.usecase.users.FindUserUseCase;
import io.github.javialc.java.labs.domain.usecase.users.FindUsersUseCase;
import io.github.javialc.java.labs.mapper.UserDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {

    private final FindUsersUseCase findUsersUseCase;
    private final FindUserUseCase findUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserDtoMapper userDtoMapper;

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        final List<User> users = findUsersUseCase.findUsers();
        return ResponseEntity.ok(userDtoMapper.toUserDtoList(users));
    }

    @Override
    public ResponseEntity<Void> deleteUser(final String email) {
        deleteUserUseCase.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDto> getUser(final String email) {
        final User user = findUserUseCase.findUser(email);
        return ResponseEntity.ok(userDtoMapper.toUserDto(user));
    }
}
