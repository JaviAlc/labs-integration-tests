package io.github.javialc.java.labs.domain.model.shop;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Bag {

    private double total;
    private final List<Product> products;

    public Bag() {
        this.products = new ArrayList<>();
        this.total = 0;
    }

    public Bag(final List<Product> products) {
        this.products = products;
        this.total = products.stream().mapToDouble(Product::price).sum();
    }

    public Bag addProduct(final Product product) {
        this.products.add(product);
        this.total = products.stream().mapToDouble(Product::price).sum();
        return this;
    }


}
