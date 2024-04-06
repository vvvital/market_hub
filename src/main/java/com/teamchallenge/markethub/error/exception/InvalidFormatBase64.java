package com.teamchallenge.markethub.error.exception;

import static com.teamchallenge.markethub.error.ErrorMessages.INVALID_FORMAT_BASE64;

public class InvalidFormatBase64 extends RuntimeException{

    public InvalidFormatBase64() {
        super(INVALID_FORMAT_BASE64);
    }
}
