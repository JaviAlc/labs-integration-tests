package io.github.javialc.java.labs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.javialc.java.labs.api.model.BagDto;
import io.github.javialc.java.labs.api.model.ProductDto;
import io.github.javialc.java.labs.api.model.PurchaseDto;
import io.github.javialc.java.labs.domain.model.shop.PurchaseStatusEnum;
import io.github.javialc.java.labs.mongodb.entity.BagVO;
import io.github.javialc.java.labs.mongodb.entity.ProductVO;
import io.github.javialc.java.labs.mongodb.entity.PurchaseVO;
import io.github.javialc.java.labs.mongodb.entity.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class ShopControllerIT extends BaseTestIT {

    public static final String PRODUCTS_PATH = "/api/shop/v1/products";
    public static final String BAGS_PATH = "/api/shop/v1/bags";
    public static final String BAGS_BUY_PATH = "/api/shop/v1/bags/buy";
    public static final String PURCHASES_PATH = "/api/shop/v1/purchases";


    @Test
    @SneakyThrows
    @WithMockUser(username = TEST_IT_MAIL, roles = "USER")
    void addProduct() {
        // Given
        final ProductDto productDto = new ProductDto();
        productDto.setName("product-name");
        productDto.setPrice(12.0);

        // When - Then
        mockMvc.perform(post(PRODUCTS_PATH)
                .contentType("application/json")
                .content(OBJECT_MAPPER.writeValueAsString(productDto)))
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {

                BagDto bagDto = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), BagDto.class);
                log.info("BagDto: {}", bagDto);
                assert bagDto.getProducts().size() == 1;
                assert bagDto.getProducts().get(0).getName().equals(productDto.getName());
                assert bagDto.getProducts().get(0).getPrice().equals(productDto.getPrice());
            });
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = TEST_IT_MAIL, roles = "USER")
    void getBag() {
        // Given
        final UserEntity userEntity = userJpaRepository.findByEmail(TEST_IT_MAIL).get();
        final List<ProductVO> productVOS = List.of(new ProductVO("product-name-one", "", 12.0),
            new ProductVO("product-name-two", "", 20.5),
            new ProductVO("product-name-three", "", 12.5));
        final BagVO bagVO = new BagVO(productVOS);
        userEntity.setBag(bagVO);
        userJpaRepository.save(userEntity);

        // When - Then
        mockMvc.perform(get(BAGS_PATH))
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
                final BagDto bagDto = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), BagDto.class);
                assert bagDto.getProducts().size() == 3;
                assert bagDto.getProducts().get(0).getName().equals("product-name-one");
                assert bagDto.getProducts().get(1).getName().equals("product-name-two");
                assert bagDto.getProducts().get(2).getName().equals("product-name-three");
                assert bagDto.getTotal().equals(45.0d);
            });
    }


    @Test
    @SneakyThrows
    @WithMockUser(username = TEST_IT_MAIL, roles = "USER")
    void buyBag() {
        // Given
        final UserEntity userEntity = userJpaRepository.findByEmail(TEST_IT_MAIL).get();
        final List<ProductVO> productVOS = List.of(new ProductVO("product-name-one", "", 12.0),
            new ProductVO("product-name-two", "", 20.5),
            new ProductVO("product-name-three", "", 12.5));
        final BagVO bagVO = new BagVO(productVOS);
        userEntity.setBag(bagVO);
        userJpaRepository.save(userEntity);

        AtomicReference<String> location = new AtomicReference<>();
        // When - Then
        mockMvc.perform(post(BAGS_BUY_PATH))
            .andExpect(status().isAccepted())
            .andExpect(mvcResult -> {
                location.set(mvcResult.getResponse().getHeader("Location"));
                log.info("Location: {}", location);
                assert location.get() != null;
            });

        // Check purchase
        mockMvc.perform(get(location.get()))
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
                final PurchaseDto purchase = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), PurchaseDto.class);
                assert purchase.getProducts().size() == 3;
                assert purchase.getTotal().equals(45.0d);
                assert purchase.getId().equalsIgnoreCase(location.get().substring(location.get().lastIndexOf("/") + 1));
                assert purchase.getStatus().equals(PurchaseDto.StatusEnum.PENDING);
                assert purchase.getDate() != null;
            });

        // Check bag is empty
        mockMvc.perform(get(BAGS_PATH))
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
                final BagDto bagDto = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), BagDto.class);
                assert bagDto.getProducts().isEmpty();
                assert bagDto.getTotal().equals(0.0d);
            });
    }


    @Test
    @SneakyThrows
    @WithMockUser(username = TEST_IT_MAIL, roles = "USER")
    void getPurchases() {
        // Given
        final UserEntity userEntity = userJpaRepository.findByEmail(TEST_IT_MAIL).get();
        final List<ProductVO> productVOS = List.of(new ProductVO("product-name-one", "", 12.0),
            new ProductVO("product-name-two", "", 20.5),
            new ProductVO("product-name-three", "", 12.5));


        PurchaseVO purchase1 = new PurchaseVO(UUID.randomUUID().toString(), Instant.now(), productVOS, 45.0, PurchaseStatusEnum.PENDING);
        PurchaseVO purchase2 = new PurchaseVO(UUID.randomUUID().toString(), Instant.now(), productVOS, 45.0, PurchaseStatusEnum.COMPLETED);

        userEntity.setPurchases(List.of(purchase1, purchase2));
        userJpaRepository.save(userEntity);

        // When - Then
        mockMvc.perform(get(PURCHASES_PATH))
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
                final List<PurchaseDto> purchases = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
                    new TypeReference<List<PurchaseDto>>() {
                    });
                assert purchases.size() == 2;
                assert purchases.get(0).getProducts().size() == 3;
                assert purchases.get(0).getTotal().equals(45.0d);
                assert purchases.get(0).getId().equalsIgnoreCase(purchase1.id());
                assert purchases.get(0).getDate() != null;
                assert purchases.get(1).getProducts().size() == 3;
                assert purchases.get(1).getTotal().equals(45.0d);
                assert purchases.get(1).getId().equalsIgnoreCase(purchase2.id());
                assert purchases.get(1).getDate() != null;
            });
    }

}
