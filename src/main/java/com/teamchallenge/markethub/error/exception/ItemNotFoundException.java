package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.repository.ItemRepository;

public class ItemNotFoundException extends Exception{

    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
