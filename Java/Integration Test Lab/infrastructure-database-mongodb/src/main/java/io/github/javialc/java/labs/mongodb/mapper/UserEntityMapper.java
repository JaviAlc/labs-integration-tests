package io.github.javialc.java.labs.mongodb.mapper;

import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.model.UserRequest;
import io.github.javialc.java.labs.domain.model.shop.Bag;
import io.github.javialc.java.labs.domain.model.shop.Product;
import io.github.javialc.java.labs.mongodb.entity.BagVO;
import io.github.javialc.java.labs.mongodb.entity.ProductVO;
import io.github.javialc.java.labs.mongodb.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toEntity(UserRequest userRequest);

    UserEntity toEntity(User user);

    @Mapping(target = "id", source = "id")
    UserEntity toEntity(String id, UserRequest userEntity);

    User toDomain(UserEntity userEntity);

    List<User> toDomain(List<UserEntity> userEntities);


    List<Product> productVOListToProductList(List<ProductVO> products);

    default Bag bagVOToBag(BagVO bagVO) {
        if (bagVO == null) {
            return null;
        }
        final List<Product> list = productVOListToProductList( bagVO.products() );
        return new Bag(list);
    }
}
