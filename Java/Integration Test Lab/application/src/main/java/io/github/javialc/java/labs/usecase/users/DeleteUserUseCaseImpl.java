package io.github.javialc.java.labs.usecase.users;

import io.github.javialc.java.labs.domain.adapters.AuthorizeServerAdapter;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.users.DeleteUserUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private UserRepository userRepository;
    private AuthorizeServerAdapter authorizeServerAdapter;

    @Override
    public void deleteUser(String email) {
        log.info("Deleting user with email: {}", email);
        userRepository.findByEmail(email)
            .ifPresentOrElse(user ->
                {
                    userRepository.delete(user.getId());
                    authorizeServerAdapter.delete(email);
                },
                () -> log.warn("User with email: {} not found", email));
    }
}
