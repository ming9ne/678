package com.sw678.crud.repository;

import com.sw678.crud.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 메소드명의 By 이후는 SQL의 where 조건 절에 대응되는데, Containing을 붙여주면 Like 검색이 가능해짐
    List<Board> findByContentContaining(String keyword);

    List<Board> findByTitleContaining(String keyword);

    List<Board> findByWriterContaining(String keyword);
}