package io.github.javialc.java.labs.authorizeserver.jpa;

import io.github.javialc.java.labs.authorizeserver.entity.AuthUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthUserJpaRepository extends MongoRepository<AuthUserEntity, String> {

    Optional<AuthUserEntity> findByEmail(String email);

    void deleteByEmail(String email);
}
