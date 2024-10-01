package io.github.javialc.java.labs.domain.model.authorization;

public record LdapAuthResponse(boolean authenticated, String message, String token) {

}
