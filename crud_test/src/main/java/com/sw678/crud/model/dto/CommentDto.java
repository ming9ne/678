package com.sw678.crud.model.dto;

import com.sw678.crud.model.entity.Board;
import com.sw678.crud.model.entity.Comment;
import com.sw678.crud.model.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private Board board;
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Comment parentComment;
    private List<Comment> childComment = new ArrayList<>();

    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .content(content)
                .parentComment(parentComment)
                .childComment(childComment)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
