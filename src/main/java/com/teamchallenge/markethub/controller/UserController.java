package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.email.EmailRequest;
import com.teamchallenge.markethub.dto.email.EmailResponse;
import com.teamchallenge.markethub.dto.password.PasswordRequest;
import com.teamchallenge.markethub.dto.password.PasswordResponse;
import com.teamchallenge.markethub.dto.user.UpdateUserDTO;
import com.teamchallenge.markethub.dto.user.UserDto;
import com.teamchallenge.markethub.email.CustomTemplates;
import com.teamchallenge.markethub.email.EmailSender;
import com.teamchallenge.markethub.error.ErrorDetails;
import com.teamchallenge.markethub.error.ResponseApiError;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.teamchallenge.markethub.error.ErrorMessages.MAIL_FAIL;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/markethub/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;
    private final EmailSender emailSender;

    //TODO: Implement controller 'userUpdate' and implement tests
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") Integer id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserDto.convertToUserResponse(user));
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> sendEmailForResetPassword(@RequestBody @Valid EmailRequest emailRequest) {
        try {
            emailSender.sendMail(
                    emailRequest.email(),
                    params(emailRequest),
                    CustomTemplates.PASSWORD_CHANGE_TEMPLATE);
            return ResponseEntity.ok(new EmailResponse("success", 200));

        } catch (MessagingException | TemplateException | IOException e) {
            log.error("sending email failed: {} ", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse());
        }
    }

    private ResponseApiError errorResponse() {
        return new ResponseApiError(new ErrorDetails(
                500, MAIL_FAIL));
    }

    private Map<String, Object> params(EmailRequest emailRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", getFullUsernameByEmail(emailRequest));
        return params;
    }

    private String getFullUsernameByEmail(EmailRequest emailRequest) {
        User user = userService.findByEmail(emailRequest.email());
        return user.getFirstname() + " " + user.getLastname();
    }

    @PutMapping("/{id}/change_password")
    public ResponseEntity<PasswordResponse> updateUserPassword(@RequestBody @Valid PasswordRequest passwordRequest,
                                                               @PathVariable(name = "id") Integer id) {
        User user = userService.findById(id);
        if (passwordRequest.password().isBlank()) {
            return ResponseEntity.status(409).build();
        }
        user.setPassword(encodePassword(passwordRequest.password()));
        userService.update(user);
        return ResponseEntity.ok(new PasswordResponse("success", 200));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> updateUser(@PathVariable (name = "id") Integer id,
                                              @RequestBody UpdateUserDTO updateUserDTO){
        User user = userService.findById(id);
        if (!updateUserDTO.getFirstname().isEmpty())user.setFirstname(updateUserDTO.getFirstname());
        if (!updateUserDTO.getLastname().isEmpty())user.setLastname(updateUserDTO.getLastname());
        if (!updateUserDTO.getEmail().isEmpty())user.setEmail(updateUserDTO.getEmail());
        if (!updateUserDTO.getPhone().isEmpty())user.setPhone(updateUserDTO.getPhone());
        if (!updateUserDTO.getPassword().isEmpty())user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder()
                .encode(updateUserDTO.getPassword()));
        return ResponseEntity.ok(UserDto.convertToUserResponse(userService.update(user)));
    }

    private String encodePassword(String password) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
                .encode(password);
    }
}
