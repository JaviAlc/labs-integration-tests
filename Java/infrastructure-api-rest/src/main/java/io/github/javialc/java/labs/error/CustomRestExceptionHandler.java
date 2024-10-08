package io.github.javialc.java.labs.error;

import io.github.javialc.java.labs.api.model.ErrorDto;
import io.github.javialc.java.labs.domain.error.AuthorizeException;
import io.github.javialc.java.labs.domain.error.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        final ErrorDto error = new ErrorDto();
        error.setCode("INVALID_ARGUMENT");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        log.warn("IllegalArgumentException: ", e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException e) {
        final ErrorDto error = new ErrorDto();
        error.setCode("INTERNAL_SERVER_ERROR");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.warn("RuntimeException: ", e);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity<ErrorDto> handleAuthorizeException(AuthorizeException e) {
        final ErrorDto error = new ErrorDto();
        error.setCode(e.getCode());
        error.setMessage("Invalid user or password");
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        log.warn("AuthorizeException: ", e);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException e) {
        final ErrorDto error = new ErrorDto();
        error.setCode(e.getCode());
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        log.warn("UserNotFoundException: ", e);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("MethodArgumentNotValidException: ", ex);

        final ErrorDto error = new ErrorDto();
        error.setCode("ARGUMENT_NOT_VALID");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        final List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .toList();
        error.setMessage("Validation failed for argument: " + fieldErrors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
