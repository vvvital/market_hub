package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.config.jwt.JwtUtils;
import com.teamchallenge.markethub.dto.authorization.AuthorizationRequest;
import com.teamchallenge.markethub.dto.user.UserResponse;
import com.teamchallenge.markethub.email.EmailSender;
import com.teamchallenge.markethub.exception.ErrorHandler;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/markethub")
public class AuthorizationController {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailSender emailSender;
    private final Map<String, Object> jsonResponse = new HashMap<>();

    public AuthorizationController(UserServiceImpl userService, AuthenticationManager authenticationManager,
                                   JwtUtils jwtUtils, EmailSender emailSender) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.emailSender = emailSender;
    }

    @PostMapping("/authorization")
    public ResponseEntity<?> sellerAuthorization(@RequestBody AuthorizationRequest authRequest,
                                                 UriComponentsBuilder uri) {

        if (userWithThisEmailIsExists(authRequest.email())) {
            log.error("User with this email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorHandler.invalidUniqueEmail());
        }

        if (userWithThisPhoneIsExist(authRequest.phone())) {
            log.error("User with this phone already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorHandler.invalidUniquePhone());
        }

        User savedSeller = userService.create(UserResponse.convertToNewSeller(authRequest));

        URI locationOfNewCashCard = uri
                .path("/markethub/users/{id}")
                .buildAndExpand(savedSeller.getId())
                .toUri();

        String jwtToken = userAuthenticationAndGenerateToken(authRequest.email(), authRequest.password());

        jsonResponse.put("status", HttpStatus.CREATED.value());
        jsonResponse.put("token", jwtToken);

        sendEmail(authRequest.email(), authRequest.firstname(), authRequest.lastname());

        return ResponseEntity.created(locationOfNewCashCard).body(jsonResponse);
    }

    private boolean userWithThisEmailIsExists(String email) {
        return Objects.nonNull(userService.findByEmail(email));
    }

    private boolean userWithThisPhoneIsExist(String phone) {
        return Objects.nonNull(userService.findByPhone(phone));
    }

    private void sendEmail(String email, String firstname, String lastname) {
        try {
            emailSender.sendEmail(email, firstname, lastname);
        } catch (MessagingException | TemplateException | IOException e) {
            log.error(e.getMessage());
        }
    }

    private String userAuthenticationAndGenerateToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(email, password));
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return jwtUtils.generateTokenFromEmail(user.getUsername());
    }

}
