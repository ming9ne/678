package com.sw678.crud.model.dto;

import com.sw678.crud.model.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String username;

    private String email;

    private String password;

    public User toEntity(){
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
