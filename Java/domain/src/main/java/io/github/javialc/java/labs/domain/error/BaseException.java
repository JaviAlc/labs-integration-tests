package io.github.javialc.java.labs.domain.error;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final String code;
    private final Object error;

    BaseException(final String message) {
        super(message);
        this.code = "GENERAL_ERROR";
        this.error = message;
    }

    BaseException(final String code, final String message, final Object error) {
        super(message);
        this.code = code;
        this.error = error;
    }
}


