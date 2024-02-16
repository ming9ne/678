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

    @PostMapping("/{id}/write")
    public String addComment(@PathVariable("id") Long id, String content, @AuthenticationPrincipal UserDetail user) {
        commentService.addComment(id, content, user.getUsername());
        System.out.println("DB 댓글 작성 완료...");
        return "redirect:/board/post/" + id;
    }

    @PostMapping("/{commentId}/{boardId}/update")
    public String editComment(@PathVariable Long commentId, @PathVariable Long boardId , String content){
        commentService.updateComment(commentId, content);
        System.out.println("DB 수정 작업 완료...");
        return "redirect:/board/post/" + boardId;
    }

    @GetMapping("/{commentId}/{boardId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId, @PathVariable Long boardId) {
        commentService.deleteComment(commentId);
        System.out.println("DB 삭제 작업 완료...");
        return "redirect:/board/post/" + boardId;
    }

    @PostMapping("/{commentId}/{boardId}/reply")
    public String addReply(@PathVariable Long commentId, @PathVariable Long boardId , String content
            , @AuthenticationPrincipal UserDetail user){

        commentService.addReply(user.getUsername(), commentId, boardId, content);
        System.out.println("DB 대댓글 작성 완료...");
        return "redirect:/board/post/" + boardId;
    }


}
