package io.github.javialc.java.labs.mapper;

import io.github.javialc.java.labs.api.model.BagDto;
import io.github.javialc.java.labs.api.model.ProductDto;
import io.github.javialc.java.labs.api.model.PurchaseDto;
import io.github.javialc.java.labs.domain.model.shop.Bag;
import io.github.javialc.java.labs.domain.model.shop.Product;
import io.github.javialc.java.labs.domain.model.shop.Purchase;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    Product toDomain(ProductDto productDto);

    ProductDto toDto(Product product);

    BagDto toDto(Bag bag);

    PurchaseDto toDto(Purchase purchase);

    List<PurchaseDto> toDto(List<Purchase> purchases);


    default Instant toInstant(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return offsetDateTime.toInstant();
    }

    default OffsetDateTime fromInstant(Instant instant) {
        if (instant == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(instant, OffsetDateTime.now().getOffset());
    }

}
