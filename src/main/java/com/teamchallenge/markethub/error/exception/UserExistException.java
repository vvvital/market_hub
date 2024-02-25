package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.error.ErrorMessages;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super(ErrorMessages.USER_EXIST.text());
    }
}
