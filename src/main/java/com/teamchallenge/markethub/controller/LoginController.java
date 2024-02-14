package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.config.jwt.JwtUtils;
import com.teamchallenge.markethub.dto.login.LoginRequest;
import com.teamchallenge.markethub.dto.login.LoginResponse;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/markethub")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        User userDetails = (User) userAuthenticate(loginRequest).getPrincipal();
        return ResponseEntity.ok().body(loginResponse(getUser(userDetails), token(userDetails)));
    }

    private Authentication userAuthenticate(LoginRequest loginRequest) {
        return authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
    }

    private com.teamchallenge.markethub.model.User getUser(User userDetails) {
        return userService.findByEmail(userDetails.getUsername());
    }

    private String token(User userDetails) {
        return jwtUtils.generateTokenFromEmail(userDetails.getUsername());
    }

    private static LoginResponse loginResponse(com.teamchallenge.markethub.model.User user, String jwtToken) {
        return new LoginResponse(200, user.getId(), user.getFirstname(),
                user.getLastname(), user.getEmail(), user.getPhone(), jwtToken);
    }
}
