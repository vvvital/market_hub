package com.teamchallenge.markethub.exception;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        log.error("{}", e.getMessage());
        ApiError apiError = new ApiError(new Error(
                404, ErrorMessages.USER_NOT_FOUND.text()));
        return ResponseEntity.status(404).body(apiError);
    }

    @ExceptionHandler(MessagingException.class)
    protected ResponseEntity<?> handleMessagingException(MessagingException e) {
        log.error("{}", e.getMessage());
        ApiError apiError = new ApiError(new Error(
                500, ErrorMessages.MAIL_FAIL.text()));
        return ResponseEntity.status(500).body(apiError);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<?> handleIOException(IOException e) {
        log.error("{}", e.getMessage());
        ApiError apiError = new ApiError(new Error(
                422, ErrorMessages.MAIL_FAIL.text()));
        return ResponseEntity.status(422).body(apiError);
    }

    @ExceptionHandler(TemplateException.class)
    protected ResponseEntity<?> handleTemplateException(TemplateException e) {
        log.error("{}", e.getMessage());
        ApiError apiError = new ApiError(new Error(
                500, ErrorMessages.MAIL_FAIL.text()));
        return ResponseEntity.status(500).body(apiError);
    }

    @ExceptionHandler(MailSendException.class)
    protected ResponseEntity<?> handleMailSendException(MailSendException e) {
        log.error("{}", e.getMessage());
        ApiError apiError = new ApiError(new Error(
                500, ErrorMessages.MAIL_FAIL.text()));
        return ResponseEntity.status(500).body(apiError);
    }

    @ExceptionHandler(UserExistException.class)
    protected ResponseEntity<?> handleUserExistException() {
        log.error("User with this email or phone already exists");
        ApiError apiError = new ApiError(new Error(
                409, ErrorMessages.USER_EXIST.text()));
        return ResponseEntity.status(409).body(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<?> handleBadCredentialException(BadCredentialsException e) {
        log.error("Bad credentials: " + e.getMessage());
        ApiError apiError = new ApiError(
                new Error(409, ErrorMessages.INCORRECT_LOGIN_DATA.text()));
        return ResponseEntity.status(409).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> handleBadCredentialException(AuthenticationException e) {
        log.error("Authentication failed: " + e.getMessage());
        ApiError apiError = new ApiError(new Error(
                409, ErrorMessages.INCORRECT_LOGIN_DATA.text()));
        return ResponseEntity.status(409).body(apiError);
    }


}
