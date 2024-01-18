package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.config.jwt.JwtUtils;
import com.teamchallenge.markethub.dto.login.LoginRequest;
import com.teamchallenge.markethub.dto.login.LoginResponse;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/markethub")
public class LoginController {
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
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        User userDetails = (User) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromEmail(userDetails.getUsername());
        com.teamchallenge.markethub.model.User user = userService.findByEmail(userDetails.getUsername());
        String username = user.getFirstname() + " " + user.getLastname();
        LoginResponse response = new LoginResponse(200, user.getId(), username, jwtToken);
        return ResponseEntity.ok().body(response);
    }
}
