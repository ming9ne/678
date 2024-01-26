package com.sw678.crud.model.entity;

import com.sw678.crud.model.dto.BoardDto;
import com.sw678.crud.model.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
// JPA에서는 프록시 생성을 위해 기본 생성자를 반드시 하나 생성해야 한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서의 생성을 열어 둘 필요가 없을 때 / 보안적으로 권장됨
@Table(name = "crud")
@Getter
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id; // 게시글 index

    // private User user_id ; 유저 인덱스


    private String writer;

    @Column(name="post_title")
    private String title; // 제목

    @Column(name="post_content")
    private String content; // 내용


    // Java 디자인 패턴, 생성 시점에 값을 채워줌
    @Builder
    public Board(Long id, String writer, String title, String content) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
