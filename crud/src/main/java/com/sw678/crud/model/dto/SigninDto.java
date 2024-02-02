package com.sw678.crud.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SigninDto {

    @NotBlank // Null, "", " " 모두 금지
    @Size(min = 2, max = 10)
    private String username;

    @NotEmpty // Null, "" 금지
    private String password;
}
