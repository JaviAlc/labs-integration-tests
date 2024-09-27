package io.github.javialc.java.labs.usecase;

import io.github.javialc.java.labs.domain.model.User;
import io.github.javialc.java.labs.domain.repository.UserRepository;
import io.github.javialc.java.labs.domain.usecase.FindUsersUseCase;
import io.github.javialc.java.labs.domain.utils.RequestSource;
import io.github.javialc.java.labs.domain.utils.RequestSourceContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class FindUserUseCaseImpl implements FindUsersUseCase {

    private final UserRepository userRepository;

    @Override
    public List<User> findUsers() {

        RequestSource requestSource = RequestSourceContextHolder.getRequestSource();
        log.info("RequestSource: {}", requestSource);

        return userRepository.findAll();
    }
}
