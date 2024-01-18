package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.email.EmailResponse;
import com.teamchallenge.markethub.dto.password.PasswordResponse;
import com.teamchallenge.markethub.dto.user.UserResponse;
import com.teamchallenge.markethub.dto.email.EmailRequest;
import com.teamchallenge.markethub.dto.password.PasswordRequest;
import com.teamchallenge.markethub.email.EmailSender;
import com.teamchallenge.markethub.email.EmailSubjects;
import com.teamchallenge.markethub.exception.UserNotFoundException;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/markethub/users")
public class UserController {

    private final UserServiceImpl userService;
    private final EmailSender emailSender;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(name = "id") Integer id) throws UserNotFoundException {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserResponse.convertToUserResponse(user));
    }

    @PostMapping("/reset_password")
    public ResponseEntity<EmailResponse> sendEmailForResetPassword(@RequestBody @Valid EmailRequest emailRequest) throws MessagingException, TemplateException, IOException {
        User user = userService.findByEmail(emailRequest.email());
        String name = user.getFirstname() + " " + user.getLastname();
        emailSender.sendMail(emailRequest.email(), name, EmailSubjects.RESET_PASSWORD);
        EmailResponse response = new EmailResponse("success", 200);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/change_password")
    public ResponseEntity<PasswordResponse> updateUserPassword(@RequestBody @Valid PasswordRequest passwordRequest,
                                                   @PathVariable(name = "id") Integer id) throws UserNotFoundException {
        User user = userService.findById(id);
        if (passwordRequest.password().isBlank()) {
            return ResponseEntity.status(409).build();
        }
        String newPassword = PasswordEncoderFactories.createDelegatingPasswordEncoder()
                .encode(passwordRequest.password());
        user.setPassword(newPassword);
        userService.update(user);
        PasswordResponse response = new PasswordResponse("success", 200);
        return ResponseEntity.ok(response);
    }

}
