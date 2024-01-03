package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.config.jwt.JwtUtils;
import com.teamchallenge.markethub.dto.login.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static com.teamchallenge.markethub.exception.ErrorHandler.invalidLoginEmail;
import static com.teamchallenge.markethub.exception.ErrorHandler.invalidLoginPassword;

@RestController
@RequestMapping("/markethub")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
            User user = (User) authentication.getPrincipal();
            String jwtToken = jwtUtils.generateTokenFromEmail(user.getUsername());
            return ResponseEntity.ok().body(jwtToken);
        } catch (BadCredentialsException e) {
            log.error("Bad credentials: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(invalidLoginPassword());
        } catch (AuthenticationException e) {
            log.error("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(invalidLoginEmail());
        }
    }
}
