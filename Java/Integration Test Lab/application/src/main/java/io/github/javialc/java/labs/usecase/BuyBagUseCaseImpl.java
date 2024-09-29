package io.github.javialc.java.labs.usecase;

import io.github.javialc.java.labs.domain.usecase.shop.BuyBagUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class BuyBagUseCaseImpl implements BuyBagUseCase {
    
    @Override
    public String buyBag() {
        return "";
    }
}
