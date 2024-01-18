package com.teamchallenge.markethub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
