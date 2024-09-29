package io.github.javialc.java.labs.domain.model;

import io.github.javialc.java.labs.domain.model.shop.Bag;
import io.github.javialc.java.labs.domain.model.shop.Purchase;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class User {

    private String id;
    private String name;
    private String email;
    private Bag bag;
    private final List<Purchase> purchases = new ArrayList<>();
}
