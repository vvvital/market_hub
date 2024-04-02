package com.teamchallenge.markethub.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateUserDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;

}
