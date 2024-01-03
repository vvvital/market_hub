package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.config.jwt.JwtUtils;
import com.teamchallenge.markethub.dto.authorization.AuthorizationRequest;
import com.teamchallenge.markethub.dto.user.UserResponse;
import com.teamchallenge.markethub.exception.ErrorHandler;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/markethub")
public class AuthorizationController {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthorizationController(UserServiceImpl userService, AuthenticationManager authenticationManager,
                                   JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/user-authorization")
    public ResponseEntity<?> sellerAuthorization(@RequestBody AuthorizationRequest authRequest,
                                                 UriComponentsBuilder uri) {
        if (userExists(authRequest.email())) {
            log.error("User with this email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorHandler.invalidUniqueEmail());
        }
        User savedSeller = userService.create(UserResponse.convertNewSeller(authRequest));

        URI locationOfNewCashCard = uri
                .path("/markethub/users/{id}")
                .buildAndExpand(savedSeller.getId())
                .toUri();

        String jwtToken = userAuthenticationAndGenerateToken(authRequest.email(), authRequest.password());
        return ResponseEntity.created(locationOfNewCashCard).body(jwtToken);
    }

    private boolean userExists(String email) {
        return Objects.nonNull(userService.findByEmail(email));
    }

    private String userAuthenticationAndGenerateToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(email, password));
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return jwtUtils.generateTokenFromEmail(user.getUsername());
    }


//    @GetMapping("/oauth2/google")
//    public Map<String,Object> getUser(OAuth2AuthenticationToken auth2AuthenticationToken) {
//        Map<String,Object> userAttributes = auth2AuthenticationToken.getPrincipal().getAttributes();
//        Object email = userAttributes.get("email");
//        System.out.println(email.toString());
//        return auth2AuthenticationToken.getPrincipal().getAttributes();
//    }

}
