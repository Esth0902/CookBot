package com.development.cookbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenCustomException extends RuntimeException{

    public ForbiddenCustomException(String message) {
            super(message);
        }

    public ForbiddenCustomException(String message, Throwable cause) {
            super(message, cause);
        }
}
