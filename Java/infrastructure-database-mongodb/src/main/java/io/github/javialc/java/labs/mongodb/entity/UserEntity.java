package io.github.javialc.java.labs.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private String id;
    private String name;
    private String email;

    @Builder.Default
    private BagVO bag = new BagVO(List.of(), 0.0);
    private List<PurchaseVO> purchases=new ArrayList<>();

}
