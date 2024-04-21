package com.teamchallenge.markethub.error.exception;

import com.teamchallenge.markethub.error.ErrorMessages;
import lombok.Getter;

@Getter
public class NotEnoughQuantityItemException extends RuntimeException {
    private final long id;

    public NotEnoughQuantityItemException(long id) {
        super(String.format("%s {%s}", ErrorMessages.NOT_ENOUGH_QUANTITY_ITEM, id));
        this.id = id;
    }

}
