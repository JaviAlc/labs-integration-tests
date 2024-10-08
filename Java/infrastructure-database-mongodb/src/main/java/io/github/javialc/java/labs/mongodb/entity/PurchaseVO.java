package io.github.javialc.java.labs.mongodb.entity;

import io.github.javialc.java.labs.domain.model.shop.PurchaseStatusEnum;

import java.time.Instant;
import java.util.List;

public record PurchaseVO(String id, Instant date, List<ProductVO> products, double total, PurchaseStatusEnum status) {

}
