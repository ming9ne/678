package com.sw678.board.model.dto;

import com.sw678.board.model.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String uid;
    private String encryptedPwd;

    public User toEntity(){
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .uid(uid)
                .encryptedPwd(encryptedPwd)
                .build();
    }
}
