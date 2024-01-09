package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.user.UserResponse;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/markethub")
public class DebugController {

    private final UserServiceImpl userService;

    public DebugController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/del/{id}")
    public void deleteUser(@PathVariable(name = "id") Integer id) {
        userService.remove(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<User> userList = userService.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            userResponseList.add(UserResponse.convertToUserResponse(user));
        }
        return ResponseEntity.ok(userResponseList);
    }
}
