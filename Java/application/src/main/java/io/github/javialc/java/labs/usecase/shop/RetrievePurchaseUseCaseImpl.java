package io.github.javialc.java.labs.usecase.shop;

import io.github.javialc.java.labs.domain.model.shop.Purchase;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.shop.RetrievePurchaseUseCase;
import io.github.javialc.java.labs.domain.utils.AuthorizationServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RetrievePurchaseUseCaseImpl implements RetrievePurchaseUseCase {

    private final AuthorizationServiceUtils authorizationServiceUtils;
    private final UserRepository userRepository;

    @Override
    public Purchase retrievePurchase(final String id) {
        String email = authorizationServiceUtils.getCurrentUsername();
        log.info("Retrieving purchase for user {}", email);
        return userRepository.findByEmail(email).filter(user -> user.getPurchases() != null)
            .map(user -> user.getPurchases().stream()
                .filter(purchase -> purchase.id().equals(id)).findFirst())
            .flatMap(purchase -> purchase)
            .orElseThrow(() -> new IllegalArgumentException("Purchase not found"));
    }
}
