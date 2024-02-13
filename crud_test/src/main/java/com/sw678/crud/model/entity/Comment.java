package com.sw678.crud.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sw678.crud.model.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comment")
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @CreatedDate // 생성 시간 자동 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate // 수정 시간 자동 저장
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parentComment; //부모 댓글

    @JsonManagedReference
    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> childComment = new ArrayList<>(); //자식 댓글들(대댓글)

    static public Comment initComment(String content, User user, Board board) {
        return Comment.builder()
                .board(board)
                .user(user)
                .content(content)
                .parentComment(null)
                .childComment(null)
                .build();
    }

    public void edit(String content) {
        System.out.println("기존 댓글 : " + this.content);
        this.content = content;
        System.out.println("수정 댓글 : " + this.content);
    }

    public CommentDto toDto(){
        return CommentDto.builder()
                .id(id)
                .content(content)
                .board(board)
                .user(user)
                .parentComment(parentComment)
                .childComment(childComment)
                .build();
    }

}
