package com.shoe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordDTO {
    private String password;
    private String re_password;
}
