package com.sw678.crud.repository;

import com.sw678.crud.model.entity.Board;
import com.sw678.crud.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard(Board board);
    Optional<Comment> findById(Long id);
}
