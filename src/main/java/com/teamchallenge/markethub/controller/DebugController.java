package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.error.exception.UserNotFoundException;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/markethub")
public class DebugController {

    private final UserServiceImpl userService;

    public DebugController(UserServiceImpl userService) {
        this.userService = userService;
        System.out.println("debug controller has been started");
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/del/{id}")
    public void deleteUser(@PathVariable(name = "id") Integer id) throws UserNotFoundException {
        userService.remove(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> userList = userService.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User currentUser : userList) {
            UserDto userDto = new UserDto(currentUser.getId(), currentUser.getFirstname(), currentUser.getLastname(),
                    currentUser.getEmail(), currentUser.getPhone(), currentUser.getRegistrationDate(), currentUser.getRole());
            userDtoList.add(userDto);
        }

        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/")
    public ResponseEntity<String> welcome (){
        return ResponseEntity.ok("Welcome to Market_Hub");
    }
}

record UserDto(Integer id, String firstname, String lastname, String email,
               String phone, LocalDateTime registrationDate, String role) {
}
