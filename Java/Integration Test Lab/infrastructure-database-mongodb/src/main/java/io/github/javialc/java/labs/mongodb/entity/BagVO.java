package io.github.javialc.java.labs.mongodb.entity;

import java.util.List;

public record BagVO(List<ProductVO> products, double total) {
}
