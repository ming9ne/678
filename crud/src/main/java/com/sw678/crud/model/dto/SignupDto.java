package com.sw678.crud.model.dto;

import com.sw678.crud.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDto {

    @NotBlank
    @Size(min = 2, max = 10)
    private String username;

    @NotEmpty
    private String password;

    @Email
    private String email;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
