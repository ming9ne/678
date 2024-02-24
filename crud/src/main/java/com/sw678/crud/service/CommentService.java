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

import java.util.*;
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

    public void addReply(String username, Long commentId, Long boardId, String content) {
        User user = userRepository.findByUsername(username);
        if(user == null)
            new UsernameNotFoundException("유저가 존재하지 않습니다.");
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        Optional<Comment> comment = commentRepository.findById(commentId);

        System.out.println("user = " + user);
        System.out.println("board = " + board);
        System.out.println("comment = " + comment);

        Comment result = Comment.builder()
                .content(content)
                .user(user)
                .board(board)
                .parent(comment.get())
                .build();

        commentRepository.save(result);
    }

    // JSON 형태로 정렬해서 넘겨주는 것
    public List<CommentDto> sortCommentList(List<CommentDto> commentDto) {

        return null;
    }


    ////////////////////////
    ///////// 연습용 ////////
    ////////////////////////


    public List<Comment> findAll(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물."));
        return commentRepository.findByBoard(board);
    }

    public List<CommentDto> getCommentTree(List<Comment> comments) {
        Map<Long, CommentDto> commentMap = new HashMap<>();
        List<CommentDto> rootComments = new ArrayList<>();

        for (Comment comment : comments) {
            System.out.println("comment = " + comment);
            CommentDto commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .board(comment.getBoard())
                    .user(comment.getUser())
                    .parent(comment.getParent())
                    .child(comment.getChild())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .build();
            
            commentMap.put(comment.getId(), commentDto);

            if (comment.getParent() == null) {
                rootComments.add(commentDto);
            }
//            else {
//                CommentDto parentDto = commentMap.get(comment.getParent().getId());
//                parentDto.getChild().add(comment);
//            }
        }

        return rootComments;
    }
}
