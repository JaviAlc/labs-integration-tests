package io.github.javialc.java.labs.authorizeserver.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auth_users")
@Data
@Builder
public class AuthUserEntity {

    @Id
    private String id;

    private String email;

    private String password;
}
