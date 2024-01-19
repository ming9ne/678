package com.sw678.crud.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "crud")
@Data
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id; // 게시글 index

    // private User user_id ; 유저 인덱스

    // private User nick_name; // 유저 닉네임

    @Column(name="post_title")
    private String title; // 제목

    @Column(name="post_content")
    private String content; // 내용


}
