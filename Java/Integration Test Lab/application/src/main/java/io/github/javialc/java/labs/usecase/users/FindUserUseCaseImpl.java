package io.github.javialc.java.labs.usecase.users;

import io.github.javialc.java.labs.domain.error.UserNotFoundException;
import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.users.FindUserUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class FindUserUseCaseImpl implements FindUserUseCase {

    private final UserRepository userRepository;

    @Override
    public User findUser(final String email) {
        return userRepository.findByEmail(email).orElseThrow(
            () -> {
                log.error("User with email: {} not found", email);
                return new UserNotFoundException("USER_NOT_FOUND", String.format("User with mail %s not found", email), null);
            }
        );
    }
}
