package io.github.javialc.java.labs.usecase.shop;

import io.github.javialc.java.labs.domain.model.shop.Bag;
import io.github.javialc.java.labs.domain.model.shop.Purchase;
import io.github.javialc.java.labs.domain.model.shop.PurchaseStatusEnum;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.shop.BuyBagUseCase;
import io.github.javialc.java.labs.domain.utils.AuthorizationServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class BuyBagUseCaseImpl implements BuyBagUseCase {


    private final AuthorizationServiceUtils authorizationServiceUtils;
    private final UserRepository userRepository;

    @Override
    public String buyBag() {
        String email = authorizationServiceUtils.getCurrentUsername();
        log.info("Buying bag for user {}", email);
        Purchase purchase = userRepository.findByEmail(email).map(user -> {
            log.info("User {} bought a bag {}", email, user.getBag());
            Purchase toPurchase = new Purchase(UUID.randomUUID().toString(),
                Instant.now(), user.getBag().getProducts(), user.getBag().getTotal(), PurchaseStatusEnum.PENDING);
            user.getPurchases().add(toPurchase);
            user.setBag(new Bag());
            userRepository.save(user);
            return toPurchase;
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return purchase.id();
    }
}
