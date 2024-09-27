package io.github.javialc.java.labs.domain.usecase;

import io.github.javialc.java.labs.domain.model.UserRequest;

public interface CreateNewUserUseCase {

    void createUser(UserRequest request);
}
