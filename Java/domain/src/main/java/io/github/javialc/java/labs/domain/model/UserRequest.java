package io.github.javialc.java.labs.domain.model;

import org.springframework.lang.NonNull;

public record UserRequest(@NonNull String name, @NonNull String email,@NonNull String password) {

}
