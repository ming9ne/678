package com.sw678.crud.model.dto;

import com.sw678.crud.model.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

// 데이터를 캡슐화한 데이터 전달 객체

@Getter
@Setter
@NoArgsConstructor
@ToString // 객체가 가지고 있는 정보나 값들을 문자열로 만들어 리턴하는 메서드
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // toEntity() : dto에서 필요한 부분을 빌더 패턴을 통해 entity로 만드는 역할
    public Board toEntity(){
        Board board = Board.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return board;
    }

    @Builder
    public BoardDto(Long id, String title, String writer, String content, LocalDateTime createdAt,
                    LocalDateTime updatedAt){
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
