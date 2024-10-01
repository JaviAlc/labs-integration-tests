package io.github.javialc.java.labs.usecase.shop;

import io.github.javialc.java.labs.domain.error.UserNotFoundException;
import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.model.shop.Bag;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.shop.RetrieveBagUseCase;
import io.github.javialc.java.labs.domain.utils.AuthorizationServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RetrieveBagUseCaseImpl implements RetrieveBagUseCase {


    private final AuthorizationServiceUtils authorizationServiceUtils;
    private final UserRepository userRepository;

    @Override
    public Bag retrieveBag() {
        String email = authorizationServiceUtils.getCurrentUsername();
        log.info("Retrieving bag for user with email: {}", email);
        return userRepository.findByEmail(email).map(User::getBag)
            .orElseThrow(() -> {
                log.error("User with email: {} not found", email);
                return new UserNotFoundException("USER_NOT_FOUND", "User not found", null);
            });
    }
}
