package io.github.javialc.java.labs.domain.usecase.shop;

import io.github.javialc.java.labs.domain.model.shop.Purchase;

import java.util.List;

public interface RetrievePurchasesUseCase {

    List<Purchase> retrievePurchases();
}
