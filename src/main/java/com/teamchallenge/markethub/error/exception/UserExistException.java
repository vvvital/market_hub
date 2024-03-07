package com.teamchallenge.markethub.error.exception;

import static com.teamchallenge.markethub.error.ErrorMessages.USER_EXIST;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super(USER_EXIST);
    }
}
