package io.github.javialc.java.labs.domain.usecase.shop;

import io.github.javialc.java.labs.domain.model.shop.Bag;
import io.github.javialc.java.labs.domain.model.shop.Product;

public interface AddProductUseCase {

    Bag addProduct(Product product);
}
