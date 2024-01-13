package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.config.jwt.JwtUtils;
import com.teamchallenge.markethub.dto.login.LoginRequest;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
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

import java.util.HashMap;
import java.util.Map;

import static com.teamchallenge.markethub.exception.ErrorHandler.invalidLoginEmail;
import static com.teamchallenge.markethub.exception.ErrorHandler.invalidLoginPassword;

@RestController
@RequestMapping("/markethub")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;

    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
            User user = (User) authentication.getPrincipal();
            String jwtToken = jwtUtils.generateTokenFromEmail(user.getUsername());
            com.teamchallenge.markethub.model.User userInfo = userService.findByEmail(user.getUsername());
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("status", HttpStatus.OK.value());
            jsonResponse.put("id", userInfo.getId());
            jsonResponse.put("username", userInfo.getFirstname() + " " + userInfo.getLastname());
            jsonResponse.put("token", jwtToken);
            return ResponseEntity.ok().body(jsonResponse);
        } catch (BadCredentialsException e) {
            log.error("Bad credentials: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(invalidLoginPassword());
        } catch (AuthenticationException e) {
            log.error("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(invalidLoginEmail());
        }
    }
}
