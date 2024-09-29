package io.github.javialc.java.labs.mongodb.repository;

import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.model.UserRequest;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.mongodb.entity.UserEntity;
import io.github.javialc.java.labs.mongodb.jpa.UserJpaRepository;
import io.github.javialc.java.labs.mongodb.mapper.UserEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryMongoImpl implements UserRepository {


    private final UserEntityMapper userEntityMapper;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(final UserRequest user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        userJpaRepository.save(userEntity);
    }

    @Override
    public List<User> findAll() {
        final List<UserEntity> userEntities = userJpaRepository.findAll();
        return userEntityMapper.toDomain(userEntities);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(userEntityMapper::toDomain);
    }

    @Override
    public void delete(final String id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public void update(final String id, final UserRequest user) {
        if (userJpaRepository.existsById(id)) {
            final UserEntity userEntity = userEntityMapper.toEntity(id, user);
            userJpaRepository.save(userEntity);
        }
    }

    @Override
    public void save(User user) {
        final UserEntity userEntity = userEntityMapper.toEntity(user);
        userJpaRepository.save(userEntity);
    }
}
