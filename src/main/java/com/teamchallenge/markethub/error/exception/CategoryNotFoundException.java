package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.error.ErrorMessages;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException() {
        super(ErrorMessages.CATEGORY_NOT_FOUND);
    }
}
