package com.sw678.board.model.entity;

import com.sw678.board.model.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @Size(max = 50, message = "id must be at most 50 characters")
    private String id;
    @NotBlank(message = "username cannot be blank")
    @Size(max = 10, message = "username must be at most 10 characters")
    private String username;
    @Email(message = "email should be valid")
    @Size(max = 50, message = "email must be at most 50 characters")
    private String email;
    @Size(max = 100, message = "uid must be at most 100 characters")
    private String uid;
    @Size(max = 100, message = "EncryptedPassword must be at most 100 characters")

    private String encryptedPwd;

    public UserDTO toDto(){
        return UserDTO.builder()
                .id(id)
                .username(username)
                .email(email)
                .uid(uid)
                .encryptedPwd(encryptedPwd)
                .build();
    }
}
