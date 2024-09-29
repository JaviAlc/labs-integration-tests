package io.github.javialc.java.labs.controller;

import io.github.javialc.java.labs.api.ShopApi;
import io.github.javialc.java.labs.api.model.BagDto;
import io.github.javialc.java.labs.api.model.ProductDto;
import io.github.javialc.java.labs.api.model.PurchaseDto;
import io.github.javialc.java.labs.domain.model.shop.Bag;
import io.github.javialc.java.labs.domain.model.shop.Product;
import io.github.javialc.java.labs.domain.model.shop.Purchase;
import io.github.javialc.java.labs.domain.usecase.shop.AddProductUseCase;
import io.github.javialc.java.labs.domain.usecase.shop.BuyBagUseCase;
import io.github.javialc.java.labs.domain.usecase.shop.RetrieveBagUseCase;
import io.github.javialc.java.labs.domain.usecase.shop.RetrievePurchaseUseCase;
import io.github.javialc.java.labs.domain.usecase.shop.RetrievePurchasesUseCase;
import io.github.javialc.java.labs.mapper.ShopMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ShopController implements ShopApi {

    private final ShopMapper shopMapper;
    private final AddProductUseCase addProductUseCase;
    private final RetrieveBagUseCase retrieveBagUseCase;
    private final RetrievePurchasesUseCase retrievePurchasesUseCase;
    private final BuyBagUseCase buyBagUseCase;
    private final RetrievePurchaseUseCase retrievePurchaseUseCase;

    @Override
    public ResponseEntity<BagDto> addProduct(final ProductDto productDto) {
        final Product product = shopMapper.toDomain(productDto);
        final Bag bag = addProductUseCase.addProduct(product);
        return ResponseEntity.ok(shopMapper.toDto(bag));
    }

    @Override
    public ResponseEntity<BagDto> getBag() {
        final Bag bag = retrieveBagUseCase.retrieveBag();
        return ResponseEntity.ok(shopMapper.toDto(bag));
    }

    @Override
    public ResponseEntity<Void> buyBag() {
        final String purchaseId = buyBagUseCase.buyBag();
        log.info("purchaseId: {}", purchaseId);
        return ResponseEntity.accepted()
                .header("Location", "/api/shop/v1/purchases/" + purchaseId)
                .build();
    }

    @Override
    public ResponseEntity<PurchaseDto> getPurchase(final String id) {
        Purchase purchase = retrievePurchaseUseCase.retrievePurchase(id);
        return ResponseEntity.ok(shopMapper.toDto(purchase));
    }

    @Override
    public ResponseEntity<List<PurchaseDto>> getPurchases() {
        final List<Purchase> purchases = retrievePurchasesUseCase.retrievePurchases();
        return ResponseEntity.ok(shopMapper.toDto(purchases));
    }

}
