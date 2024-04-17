package com.teamchallenge.markethub.error;

public final class ErrorMessages {
    public static final String USER_NOT_FOUND = "user_not_found";
    public static final String USER_EXIST = "user_already_exist";
    public static final String MAIL_FAIL = "mail_fail";
    public static final String INCORRECT_LOGIN_DATA = "incorrect_username_password";
    public static final String INCORRECT_TOKEN = "incorrect_access_token";
    public static final String INCORRECT_PROPERTY_REFERENCE = "incorrect_property_reference";
    public static final String USER_FAILED_UPDATE = "failed_update_user_data";
    public static final String CATEGORY_NOT_FOUND = "category_not_found";
    public static final String SUB_CATEGORY_NOT_FOUND = "sub_category_not_found";
    public static final String ITEM_NOT_FOUND = "item_not_found";
    public static final String INVALID_FORMAT_BASE64 = "invalid_format_base64";
    public static final String EMPTY_ARRAY_DATA_ABOUT_ORDERED_ITEMS = "array_data_about_ordered_items_must_not_be_empty";
    public static final String NOT_ENOUGH_QUANTITY_ITEM = "not_enough_quantity_item";
    public static final String ORDER_NOT_FOUND = "order_not_found";

    private ErrorMessages() {
    }

}
