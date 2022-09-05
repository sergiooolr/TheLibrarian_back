package com.thelibrarian.data.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class LoginDto {
    @NotNull
    @Email
    private String correo;
    @NotEmpty
    private String password;
}
