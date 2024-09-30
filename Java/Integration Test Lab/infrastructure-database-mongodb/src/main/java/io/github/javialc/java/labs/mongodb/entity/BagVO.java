package io.github.javialc.java.labs.mongodb.entity;

import java.util.List;

public record BagVO(List<ProductVO> products, double total) {

    public BagVO(List<ProductVO> products) {
        this(products, products.stream().mapToDouble(ProductVO::price).sum());
    }
}
