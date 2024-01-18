package com.teamchallenge.markethub.exception;

public enum ErrorMessages {
    USER_NOT_FOUND("user_not_found"),
    USER_EXIST("user_already_exist"),
    MAIL_FAIL("mail_fail"),
    INCORRECT_LOGIN_DATA("incorrect_username_password"),
    INCORRECT_TOKEN("incorrect_access_token"),
    USER_FAILED_UPDATE("failed_update_user_data");

    private final String msg;

    ErrorMessages(String msg) {
        this.msg = msg;
    }

    public String text() {
        return this.msg;
    }
}
