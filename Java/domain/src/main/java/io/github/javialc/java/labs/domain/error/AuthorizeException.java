package io.github.javialc.java.labs.domain.error;

public class AuthorizeException extends BaseException {
    public AuthorizeException(String message) {
        super(message);
    }

    public AuthorizeException(String code, String message, Object error) {
        super(code, message, error);
    }
}
