package com.sw678.crud.model.dto;

import com.sw678.crud.model.entity.Role;
import com.sw678.crud.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignupDto {

    @NotBlank
    @Size(min = 2, max = 30)
    private String username;

    private String nickname;

    @NotEmpty
    private String password;

    @Email
    private String email;
    private Role role;
    private LocalDateTime createDate;

    public User toEntity() {
        return User.builder()
                .username(username)
                .nickname(username)
                .password(password)
                .email(email)
                .role(role.USER)
                .createDate(LocalDateTime.now())
                .build();
    }
}
