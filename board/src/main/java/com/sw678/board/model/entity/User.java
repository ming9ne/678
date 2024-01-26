package com.sw678.board.model.entity;

import com.sw678.board.model.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id

    private String id;

    private String username;

    private String email;

    private String uid;

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
