package io.github.javialc.java.labs.domain.usecase;

import io.github.javialc.java.labs.domain.model.LoginRequest;
import io.github.javialc.java.labs.domain.model.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}
