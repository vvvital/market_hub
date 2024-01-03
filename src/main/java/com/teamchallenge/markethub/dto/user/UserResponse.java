package com.teamchallenge.markethub.dto.user;

import com.teamchallenge.markethub.dto.authorization.AuthorizationRequest;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.model.role.Role.java.Role;

public record UserResponse(String firstname, String lastname, String email, String phone) {

    public static UserResponse convertUserResponse(User user) {
        return new UserResponse(user.getFirstname(),
                user.getLastname(), user.getEmail(),user.getPhone());
    }

    public static User convertNewSeller(AuthorizationRequest authorizationRequest) {
        User seller = new User();
        seller.setFirstname(authorizationRequest.firstname());
        seller.setLastname(authorizationRequest.lastname());
        seller.setEmail(authorizationRequest.email());
        seller.setPhone(authorizationRequest.phone());
        seller.setPassword(authorizationRequest.password());
        seller.setRole(Role.SELLER);
        return seller;
    }
}
