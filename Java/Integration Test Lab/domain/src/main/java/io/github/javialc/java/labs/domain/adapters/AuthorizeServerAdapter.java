package io.github.javialc.java.labs.domain.adapters;

import io.github.javialc.java.labs.domain.model.LdapAuthResponse;

public interface AuthorizeServerAdapter {

    boolean exists(String email);

    LdapAuthResponse authenticate(String email, String password);

    void create(String email, String password);


    boolean validateToken(String token);

}
