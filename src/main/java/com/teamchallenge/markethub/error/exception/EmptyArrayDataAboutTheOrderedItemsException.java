package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.error.ErrorMessages;

public class EmptyArrayDataAboutTheOrderedItemsException extends RuntimeException {
    public EmptyArrayDataAboutTheOrderedItemsException() {
        super(ErrorMessages.EMPTY_ARRAY_DATA_ABOUT_ORDERED_ITEMS);
    }
}
