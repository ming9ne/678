package com.sw678.crud.service;

import com.sw678.crud.model.dto.CommentDto;
import com.sw678.crud.model.entity.Board;
import com.sw678.crud.model.entity.Comment;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.repository.BoardRepository;
import com.sw678.crud.repository.CommentRepository;
import com.sw678.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    public Long addComment(Long id, String content, String username){
        User user = userRepository.findByUsername(username);
        if(user == null)
            new UsernameNotFoundException("유저가 존재하지 않습니다.");
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        Comment result = Comment.builder()
                .content(content)
                .user(user)
                .board(board)
                .build();
        System.out.println("result = " + result);
        commentRepository.save(result);
        return result.getId();
    }

    public List<CommentDto> loadCommentList(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물."));
        List<Comment> comments = commentRepository.findByBoard(board);

        return comments.stream()
                .map(comment -> comment.toDto())
                .collect(Collectors.toList());
    }


    public void updateComment(Long id, String content) {
        Optional<Comment> comment = commentRepository.findById(id);
        System.out.println("comment = " + comment);
        comment.get().edit(content);
        commentRepository.save(comment.get());
    }

    public void deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        System.out.println("comment = " + comment);
        commentRepository.deleteById(id);
    }

}
