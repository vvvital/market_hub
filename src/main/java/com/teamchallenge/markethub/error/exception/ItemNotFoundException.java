package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.error.ErrorMessages;
import com.teamchallenge.markethub.repository.ItemRepository;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException() {
        super(ErrorMessages.ITEM_NOT_FOUND);
    }
}
