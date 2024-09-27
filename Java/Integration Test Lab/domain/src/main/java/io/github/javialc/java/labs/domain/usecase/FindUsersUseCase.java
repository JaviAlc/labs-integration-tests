package io.github.javialc.java.labs.domain.usecase;

import io.github.javialc.java.labs.domain.model.User;

import java.util.List;

public interface FindUsersUseCase {
    List<User> findUsers();
}
