package io.github.javialc.java.labs.authorizeserver.adapter;

import io.github.javialc.java.labs.authorizeserver.entity.AuthUserEntity;
import io.github.javialc.java.labs.authorizeserver.jpa.AuthUserJpaRepository;
import io.github.javialc.java.labs.authorizeserver.provider.JwtTokenProvider;
import io.github.javialc.java.labs.domain.adapters.AuthorizeServerAdapter;
import io.github.javialc.java.labs.domain.model.authorization.LdapAuthResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AuthorizeServerAdapterImpl implements AuthorizeServerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserJpaRepository authUserJpaRepository;

    @Override
    public boolean exists(final String email) {
        log.info("Checking if user with email: {} exists", email);
        return authUserJpaRepository.findByEmail(email).isPresent();
    }

    @Override
    public LdapAuthResponse authenticate(final String email, final String password) {
        log.info("Authenticating user with email: {}", email);

        return authUserJpaRepository.findByEmail(email).map(user -> {
            if (user.getPassword().equals(password)) {
                return new LdapAuthResponse(true, "", jwtTokenProvider.createToken(email));
            } else {
                return new LdapAuthResponse(false, "Invalid password", null);
            }
        }).orElse(new LdapAuthResponse(false, "User not found", null));
    }

    @Override
    public void create(final String email, final String password) {
        log.info("Creating new user with email: {}", email);
        if (exists(email)) {
            log.error("User with email: {} already exists", email);
            throw new IllegalArgumentException("User already exists");
        }
        authUserJpaRepository.save(AuthUserEntity.builder().email(email).password(password).build());

    }

    @Override
    public boolean validateToken(final String token) {
        log.info("Validating token: {}", token);
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public void delete(final String email) {
        log.info("Deleting user with email: {}", email);
        authUserJpaRepository.deleteByEmail(email);
    }
}
