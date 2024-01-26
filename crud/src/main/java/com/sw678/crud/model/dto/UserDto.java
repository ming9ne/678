package com.sw678.crud.model.dto;

import com.sw678.crud.model.entity.Role;
import com.sw678.crud.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;

    @NotBlank(message = "별명을 입력하세요")
    private String username;

    @NotBlank(message = "E-Mail을 입력하세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 15, message = "8 ~ 15자 아래로 입력해주세요")
    private String password;

   // private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // toEntity() 메서드를 통해 Service -> DB(Entity)로 Data를 전달할 때 Dto를 통해서 전달
    public User toEntity(){
        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
            //    .role(role)
                .build();
        return user;
    }

    @Builder
    public UserDto(Long id, String username, String email, String password, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
