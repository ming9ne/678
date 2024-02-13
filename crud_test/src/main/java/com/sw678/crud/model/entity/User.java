package com.sw678.crud.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", length = 50)
    private String username;
    @Column(name = "password", length = 1000)
    private String password;
    private String email;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String token;
    private String provider;    // OAuth Type(google, kakao)
    private String provideId;   // OAuth key(id)
    private LocalDateTime createDate;

    @Builder
    public User(Long id, String username, String password, String email, String nickname, Role role,
                String token, String provider, String provideId, LocalDateTime createDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.token = token;
        this.provider = provider;
        this.provideId = provideId;
        this.createDate = createDate;
    }
}