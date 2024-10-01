package io.github.javialc.java.labs.usecase.authorization;

import io.github.javialc.java.labs.domain.adapters.AuthorizeServerAdapter;
import io.github.javialc.java.labs.domain.model.UserRequest;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.authorization.CreateNewUserUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CreateNewUseCaseImpl implements CreateNewUserUseCase {

    private final UserRepository userRepository;
    private final AuthorizeServerAdapter ldapAdapter;

    @Override
    public void createUser(UserRequest request) {

        log.info("Creating new user with email: {}", request.email());
        if (ldapAdapter.exists(request.email())) {
            log.error("User with email: {} already exists", request.email());
            throw new IllegalArgumentException("User already exists");
        }

        ldapAdapter.create(request.email(), request.password());
        userRepository.save(request);
    }
}
