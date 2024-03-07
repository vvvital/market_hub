package com.teamchallenge.markethub.error;

import com.teamchallenge.markethub.error.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.teamchallenge.markethub.error.ErrorMessages.*;


@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        log.error("{}", e.getMessage());
        ResponseApiError responseApiError = new ResponseApiError(
                new ErrorDetails(404, USER_NOT_FOUND));
        return ResponseEntity.status(404).body(responseApiError);
    }

    @ExceptionHandler(UserExistException.class)
    protected ResponseEntity<?> handleUserExistException() {
        log.error("User with this email or phone already exists");
        ResponseApiError responseApiError = new ResponseApiError(new ErrorDetails(
                409, USER_EXIST));
        return ResponseEntity.status(409).body(responseApiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<?> handleBadCredentialException(BadCredentialsException e) {
        log.error("Bad credentials: " + e.getMessage());
        ResponseApiError responseApiError = new ResponseApiError(
                new ErrorDetails(409, INCORRECT_LOGIN_DATA));
        return ResponseEntity.status(409).body(responseApiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        log.error("Authentication failed: " + e.getMessage());
        ResponseApiError responseApiError = new ResponseApiError(new ErrorDetails(
                409, INCORRECT_LOGIN_DATA));
        return ResponseEntity.status(409).body(responseApiError);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    protected ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException e) {
        log.error("{}", e.getMessage());
        ResponseApiError responseApiError = new ResponseApiError(new ErrorDetails(
                404, CATEGORY_NOT_FOUND));
        return ResponseEntity.status(404).body(responseApiError);
    }

    @ExceptionHandler(SubCategoryNotFoundException.class)
    protected ResponseEntity<?> handleSubCategoryNotFoundException(SubCategoryNotFoundException e) {
        log.error("{}", e.getMessage());
        ResponseApiError responseApiError = new ResponseApiError(new ErrorDetails(
                404, SUB_CATEGORY_NOT_FOUND));
        return ResponseEntity.status(404).body(responseApiError);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    protected ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException e) {
        log.error("{}", e.getMessage());
        ResponseApiError responseApiError = new ResponseApiError(new ErrorDetails(
                404, ITEM_NOT_FOUND));
        return ResponseEntity.status(404).body(responseApiError);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<ResponseApiError> handlePropertyReferenceException(PropertyReferenceException e) {
        log.error("{}", e.getMessage());
        ResponseApiError responseApiError = new ResponseApiError(new ErrorDetails(
                400, INCORRECT_PROPERTY_REFERENCE));
        return ResponseEntity.status(400).body(responseApiError);
    }
}
