package com.sw678.crud.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sw678.crud.model.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Table(name = "comment")
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent; // 부모 댓글

    @JsonManagedReference
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> child = new ArrayList<>(); // 자식 댓글들(대댓글)

    static public Comment initComment(String content, User user, Board board){
        return Comment.builder()
                .board(board)
                .user(user)
                .content(content)
                .parent(null)
                .build();
    }

    public void edit(String content){
        System.out.println("기존 댓글 :" + this.content);
        this.content = content;
        System.out.println("수정 댓글 : "+ this.content);
    }

    public CommentDto toDto(){
        return CommentDto.builder()
                .id(id)
                .content(content)
                .board(board)
                .user(user)
                .parent(parent)
                .child(child)
                .build();
    }
}
