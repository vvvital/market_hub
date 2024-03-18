package com.teamchallenge.markethub.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.dto.authorization.AuthorizationRequest;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.model.enums.Role;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserDto(String firstname, String lastname, String email, String phone,
                      LocalDateTime registrationDate, String role) {

    public static UserDto convertToUserResponse(User user) {
        return new UserDto(user.getFirstname(),
                user.getLastname(), user.getEmail(), user.getPhone(), user.getRegistrationDate(), user.getRole());
    }

    public static User convertToUser(AuthorizationRequest authorizationRequest) {
        User seller = new User();
        seller.setFirstname(authorizationRequest.firstname());
        seller.setLastname(authorizationRequest.lastname());
        seller.setEmail(authorizationRequest.email());
        seller.setPhone(authorizationRequest.phone());
        seller.setPassword(authorizationRequest.password());
        seller.setRole(Role.SELLER.name());
        return seller;
    }
}
