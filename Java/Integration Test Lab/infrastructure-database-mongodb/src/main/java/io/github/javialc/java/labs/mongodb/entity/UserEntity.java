package io.github.javialc.java.labs.mongodb.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@Builder
public class UserEntity {

    @Id
    private String id;
    private String name;
    private String email;

}
