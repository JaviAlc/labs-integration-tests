package io.github.javialc.java.labs.usecase;

import io.github.javialc.java.labs.domain.model.shop.Bag;
import io.github.javialc.java.labs.domain.model.shop.Product;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.shop.AddProductUseCase;
import io.github.javialc.java.labs.domain.utils.AuthorizationServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AddProductUseCaseImpl implements AddProductUseCase {

    private final UserRepository userRepository;
    private final AuthorizationServiceUtils authorizationServiceUtils;


    @Override
    public Bag addProduct(Product product) {
        String email = authorizationServiceUtils.getCurrentUsername();
        log.info("Adding product with name: {} for user with email: {}", product.name(), email);
        return userRepository.findByEmail(email).map(
                user -> {
                    user.getBag().addProduct(product);
                    userRepository.save(user);
                    return user.getBag();
                }).orElseThrow(
                () -> {
                    log.error("User with email: {} not found", email);
                    return new IllegalArgumentException("User not found");
                }
        );


    }
}
