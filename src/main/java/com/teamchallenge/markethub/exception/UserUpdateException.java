package com.teamchallenge.markethub.exception;

public class UserUpdateException extends Exception {

    public UserUpdateException() {
        super(ErrorMessages.USER_FAILED_UPDATE.text());
    }
}
