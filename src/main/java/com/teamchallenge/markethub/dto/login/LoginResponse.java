package com.teamchallenge.markethub.dto.login;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record LoginResponse(int code, Integer id, String firstname, String lastname, String email,
        String phone, String token){}

