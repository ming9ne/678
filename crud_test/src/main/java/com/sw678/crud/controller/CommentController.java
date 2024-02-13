package com.sw678.crud.controller;

import com.sw678.crud.model.dto.CommentDto;
import com.sw678.crud.model.dto.SigninDto;
import com.sw678.crud.model.entity.Comment;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.repository.CommentRepository;
import com.sw678.crud.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/{id}/write")
    public String addComment(@PathVariable("id") Long id, String content, @AuthenticationPrincipal UserDetail user) {
        System.out.println(user);
        commentService.addComment(id, content, user.getUsername());
        return "redirect:/board/post/" + id;
    }

    @PostMapping("/{commentId}/{boardId}/update")
    public String editComment(@PathVariable Long commentId, @PathVariable Long boardId , String content){

        commentService.updateComment(commentId, content);
        System.out.println("update Success!");
        return "redirect:/board/post/" + boardId;
    }

    @GetMapping("/{commentId}/{boardId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId, @PathVariable Long boardId
            , @AuthenticationPrincipal UserDetail user) {

        commentService.deleteComment(commentId);
        return "redirect:/board/post/" + boardId;
    }
}
