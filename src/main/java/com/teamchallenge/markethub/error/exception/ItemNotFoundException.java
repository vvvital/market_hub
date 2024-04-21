package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.error.ErrorMessages;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException() {
        super(ErrorMessages.ITEM_NOT_FOUND);
    }
}
