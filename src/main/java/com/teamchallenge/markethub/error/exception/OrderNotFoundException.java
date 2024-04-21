package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.error.ErrorMessages;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException() {
        super(ErrorMessages.ORDER_NOT_FOUND);
    }
}
