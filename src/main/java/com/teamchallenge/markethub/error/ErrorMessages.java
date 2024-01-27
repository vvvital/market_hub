package com.teamchallenge.markethub.error;

public enum ErrorMessages {
    USER_NOT_FOUND("user_not_found"),
    USER_EXIST("user_already_exist"),
    MAIL_FAIL("mail_fail"),
    INCORRECT_LOGIN_DATA("incorrect_username_password"),
    INCORRECT_TOKEN("incorrect_access_token"),
    USER_FAILED_UPDATE("failed_update_user_data"),
    CATEGORY_NOT_FOUND("category_not_found"),
    SUB_CATEGORY_NOT_FOUND("sub_category_not_found"),
    ITEM_NOT_FOUND("item_not_found");

    private final String msg;

    ErrorMessages(String msg) {
        this.msg = msg;
    }

    public String text() {
        return this.msg;
    }
}
