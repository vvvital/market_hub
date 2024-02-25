package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.repository.ItemRepository;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
