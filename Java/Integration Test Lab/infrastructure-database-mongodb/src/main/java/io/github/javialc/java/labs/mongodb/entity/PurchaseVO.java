package io.github.javialc.java.labs.mongodb.entity;

import io.github.javialc.java.labs.domain.model.shop.Product;

import java.time.Instant;
import java.util.List;

public record PurchaseVO(String id, Instant date, List<Product> products, double total) {
}
