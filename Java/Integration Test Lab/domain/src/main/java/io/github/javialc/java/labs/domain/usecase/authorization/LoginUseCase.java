package io.github.javialc.java.labs.domain.usecase.authorization;

import io.github.javialc.java.labs.domain.model.authorization.LoginRequest;
import io.github.javialc.java.labs.domain.model.authorization.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}
