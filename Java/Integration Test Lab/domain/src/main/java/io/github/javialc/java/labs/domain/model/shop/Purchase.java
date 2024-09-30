package io.github.javialc.java.labs.domain.model.shop;

import java.time.Instant;
import java.util.List;

public record Purchase(String id, Instant date, List<Product> products, double total, PurchaseStatusEnum status) {
}
