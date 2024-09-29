package io.github.javialc.java.labs.usecase;

import io.github.javialc.java.labs.domain.error.UserNotFoundException;
import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.model.shop.Purchase;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.shop.RetrievePurchasesUseCase;
import io.github.javialc.java.labs.domain.utils.AuthorizationServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class RetrievePurchasesUseCaseImpl implements RetrievePurchasesUseCase {

    private final AuthorizationServiceUtils authorizationServiceUtils;
    private final UserRepository userRepository;


    @Override
    public List<Purchase> retrievePurchases() {
        String email = authorizationServiceUtils.getCurrentUsername();
        log.info("Retrieving purchases for user with email: {}", email);
        return userRepository.findByEmail(email).map(User::getPurchases).orElseThrow(() -> {
            log.error("User with email: {} not found", email);
            return new UserNotFoundException("USER_NOT_FOUND", "User not found", null);
        });
    }
}
