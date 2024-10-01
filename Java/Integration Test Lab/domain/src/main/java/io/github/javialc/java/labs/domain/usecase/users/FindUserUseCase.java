package io.github.javialc.java.labs.domain.usecase.users;

import io.github.javialc.java.labs.domain.model.User;

public interface FindUserUseCase {

    User findUser(String email);
}
