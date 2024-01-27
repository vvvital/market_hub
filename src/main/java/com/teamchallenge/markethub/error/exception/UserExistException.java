package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.error.ErrorMessages;

public class UserExistException extends Exception {
    public UserExistException() {
        super(ErrorMessages.USER_EXIST.text());
    }
}