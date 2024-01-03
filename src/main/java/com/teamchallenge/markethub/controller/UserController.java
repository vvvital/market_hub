package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.user.UserResponse;
import com.teamchallenge.markethub.exception.UserNotFoundException;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/markethub/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(name = "id") Integer id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(UserResponse.convertUserResponse(user));
        } catch (UserNotFoundException e) {
            log.error("{}", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
