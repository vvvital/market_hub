package com.teamchallenge.markethub.exception;

public class UserExistException extends Exception {
    public UserExistException() {
        super(ErrorMessages.USER_EXIST.text());
    }
}
