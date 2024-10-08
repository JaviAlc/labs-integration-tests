package io.github.javialc.java.labs.mongodb.jpa;

import io.github.javialc.java.labs.mongodb.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserJpaRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
