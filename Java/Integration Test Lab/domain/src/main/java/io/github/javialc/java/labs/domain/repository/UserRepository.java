package io.github.javialc.java.labs.domain.repository;

import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.model.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(UserRequest user);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    void delete(String id);

    void update(String id, UserRequest user);

    void save(User user);
}
