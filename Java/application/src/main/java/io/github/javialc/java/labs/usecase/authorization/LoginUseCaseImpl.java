package io.github.javialc.java.labs.usecase.authorization;

import io.github.javialc.java.labs.domain.adapters.AuthorizeServerAdapter;
import io.github.javialc.java.labs.domain.error.AuthorizeException;
import io.github.javialc.java.labs.domain.model.authorization.LdapAuthResponse;
import io.github.javialc.java.labs.domain.model.authorization.LoginRequest;
import io.github.javialc.java.labs.domain.model.authorization.LoginResponse;
import io.github.javialc.java.labs.domain.usecase.authorization.LoginUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class LoginUseCaseImpl implements LoginUseCase {


    private final AuthorizeServerAdapter ldapAdapter;

    @Override
    public LoginResponse login(final LoginRequest request) {
        final LdapAuthResponse ldapAuthResponse = ldapAdapter.authenticate(request.email(), request.password());
        if (!ldapAuthResponse.authenticated()) {
            throw new AuthorizeException("AUTHENTICATION_ERROR", ldapAuthResponse.message(), null);
        }
        return new LoginResponse(ldapAuthResponse.token());
    }
}
