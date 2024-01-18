package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.config.jwt.JwtUtils;
import com.teamchallenge.markethub.dto.authorization.AuthorizationRequest;
import com.teamchallenge.markethub.dto.authorization.AuthorizationResponse;
import com.teamchallenge.markethub.dto.user.UserResponse;
import com.teamchallenge.markethub.email.EmailSender;
import com.teamchallenge.markethub.email.EmailSubjects;
import com.teamchallenge.markethub.exception.UserExistException;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/markethub")
public class AuthorizationController {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailSender emailSender;

    @PostMapping("/authorization")
    public ResponseEntity<?> sellerAuthorization(@RequestBody @Valid AuthorizationRequest authRequest,
                                                 UriComponentsBuilder uri) throws UserExistException {

        checkUserExist(authRequest.email(), authRequest.phone());
        User savedSeller = userService.create(UserResponse.convertToNewSeller(authRequest));
        URI locationOfNewCashCard = uri
                .path("/markethub/users/{id}")
                .buildAndExpand(savedSeller.getId())
                .toUri();
        String jwtToken = userAuthenticationAndGenerateToken(authRequest.email(), authRequest.password());
        String username = savedSeller.getFirstname() + " " + savedSeller.getLastname();
        AuthorizationResponse response = new AuthorizationResponse(201, username, jwtToken);
        sendEmail(authRequest.email(), authRequest.firstname(), authRequest.lastname());
        return ResponseEntity.created(locationOfNewCashCard).body(response);
    }

    private void checkUserExist(String email, String phone) throws UserExistException {
        if (userService.findByEmailAndPhone(email, phone)) {
            throw new UserExistException();
        }
    }

    private void sendEmail(String email, String firstname, String lastname) {
        try {
            String name = firstname + " " + lastname;
            emailSender.sendAsyncMail(email, name, EmailSubjects.WELCOME);
        } catch (MessagingException | TemplateException | IOException | MailSendException e) {
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
