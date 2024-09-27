package io.github.javialc.java.labs.domain.model;

public record LdapAuthResponse(boolean authenticated, String message, String token) {

}
