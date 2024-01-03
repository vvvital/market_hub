package com.teamchallenge.markethub.dto.authorization;

public record AuthorizationRequest(String firstname,String lastname, String email, String phone, String password) {
}
