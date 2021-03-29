package ru.giftcards.giftcards_back.auth.errors;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    public UserAlreadyExistAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserAlreadyExistAuthenticationException(String msg) {
        super(msg);
    }
}
