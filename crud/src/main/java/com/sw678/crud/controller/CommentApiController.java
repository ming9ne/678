package com.sw678.crud.controller;

import com.sw678.crud.model.dto.CommentDto;
import com.sw678.crud.model.entity.Comment;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment-service")
public class CommentApiController {
    private final CommentService commentService;

    @GetMapping("/comments/{boardId}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long boardId, Model model,
                                                           @AuthenticationPrincipal UserDetail user){
        List<Comment> comments = commentService.findAll(boardId);
        List<CommentDto> commentDto = commentService.getCommentTree(comments);
        model.addAttribute("user", user);
        return ResponseEntity.ok(commentDto);

    }

}
