package io.github.javialc.java.labs.domain.error;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String code, String message, Object error) {
        super(code, message, error);
    }
}
