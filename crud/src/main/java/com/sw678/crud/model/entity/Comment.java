package com.sw678.crud.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@Table(name = "comment")
//@Entity
public class Comment extends BaseTimeEntity {

//    @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
    private Board boards;

//    @ManyToOne
//    @JoinColumn(name = "writer")
    private Board writers;


}
