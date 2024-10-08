package io.github.javialc.java.labs.domain.usecase.shop;

import io.github.javialc.java.labs.domain.model.shop.Purchase;

public interface RetrievePurchaseUseCase {

    Purchase retrievePurchase(String id);
    
}
