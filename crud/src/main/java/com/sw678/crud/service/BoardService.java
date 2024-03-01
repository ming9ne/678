//package com.sw678.crud.service;
//
//import com.sw678.crud.model.dto.BoardDto;
//import com.sw678.crud.model.entity.Board;
//import com.sw678.crud.model.entity.User;
//
//import com.sw678.crud.repository.BoardRepository;
//import com.sw678.crud.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
////@RequiredArgsConstructor
//@AllArgsConstructor
//public class BoardService {
//
//    private final BoardRepository boardRepository;
//    private final UserRepository userRepository;
//
//    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
//    private static final int PAGE_POST_COUNT = 10; // 한 페이지에 존재하는 게시글 수
//
//    // Entity -> Dto로 변환
//    private BoardDto convertEntityToDto(Board board) {
//        return BoardDto.builder()
//                .id(board.getId())
//                .title(board.getTitle())
//                .content(board.getContent())
//                .writer(board.getWriter())
//                .createdAt(board.getCreatedAt())
//                .updatedAt(board.getUpdatedAt())
//                .build();
//    }
//
//    @Transactional
//    public List<BoardDto> getBoardList(Integer pageNum) {
//        Page<Board> page = boardRepository.findAll(PageRequest.of(
//                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdAt")
//        ));
//
//        List<Board> boardEntities = page.getContent();
//        List<BoardDto> boardDtoList = new ArrayList<>();
//
//        for (Board board : boardEntities) {
//            boardDtoList.add(this.convertEntityToDto(board));
//        }
//
//        return boardDtoList;
//    }
//
//    @Transactional
//    public BoardDto getPost(Long id) {
//        // Optional : NPE(NullPointerException) 방지
//        Optional<Board> boardWrapper = boardRepository.findById(id);
//        Board board = boardWrapper.get();
//
//        BoardDto boardDto = BoardDto.builder()
//                .id(board.getId())
//                .title(board.getTitle())
//                .content(board.getContent())
//                .writer(board.getWriter())
//                .createdAt(board.getCreatedAt())
//                .updatedAt(board.getUpdatedAt())
//                .build();
//
//        return boardDto;
//    }
//
//
//    // create or update
//    @Transactional
//    public Long savePost(BoardDto boardDto, Long id) {
//        Optional<User> user = userRepository.findById(id);
//        Board board = boardDto.toEntity();
//        board.setUser(user.get());
//        return boardRepository.save(board).getId();
//    }
//
//    //delete
//    @Transactional
//    public void deletePost(Long id) {
//        boardRepository.deleteById(id);
//    }
//
//    // paging
//    @Transactional
//    public Long getBoardCount() {
//        return boardRepository.count();
//    }
//
//    public Integer[] getPageList(Integer currentPageNum) {
//        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
//
//        // 총 게시글 갯수
//        Double postsTotalCount = Double.valueOf(this.getBoardCount());
//
//        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
//        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / PAGE_POST_COUNT)));
//
//        // 현재 페이지를 기준으로 리스트의 마지막 페이지 번호 계산
//        Integer blockLastPageNum = (totalLastPageNum > currentPageNum + BLOCK_PAGE_NUM_COUNT)
//                ? currentPageNum + BLOCK_PAGE_NUM_COUNT
//                : totalLastPageNum;
//
//        // 페이지 시작 번호 조정
//        currentPageNum = (currentPageNum <= 3) ? 1 : currentPageNum - 2;
//
//        // 페이지 번호 할당
//        for (int val = currentPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
//            pageList[idx] = val;
//        }
//
//        return pageList;
//    }
//
//
//
//
//}
package com.sw678.crud.service;

import com.sw678.crud.model.dto.BoardDto;
import com.sw678.crud.model.entity.Board;
import com.sw678.crud.model.entity.User;

import com.sw678.crud.repository.BoardRepository;
import com.sw678.crud.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10; // 한 페이지에 존재하는 게시글 수

    // Entity -> Dto로 변환
    private BoardDto convertEntityToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    @Transactional
    public List<BoardDto> getBoardList(Integer pageNum) {
        Page<Board> page = boardRepository.findAll(PageRequest.of(
                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdAt")
        ));

        List<Board> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    @Transactional
    public BoardDto getPost(Long id) {
        // Optional : NPE(NullPointerException) 방지
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();

        return boardDto;
    }


    // create or update
    @Transactional
    public Long savePost(BoardDto boardDto, Long id) {
        Optional<User> user = userRepository.findById(id);
        Board board = boardDto.toEntity();
        board.setUser(user.get());
        return boardRepository.save(board).getId();
    }

    //delete
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    // paging
    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer[] getPageList(Integer currentPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 리스트의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > currentPageNum + BLOCK_PAGE_NUM_COUNT)
                ? currentPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        currentPageNum = (currentPageNum <= 3) ? 1 : currentPageNum - 2;

        // 페이지 번호 할당
        for (int val = currentPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    // search
    // 1. 컨텐츠(내용)으로 검색
    @Transactional
    public List<BoardDto> searchC(String keyword){
        List<Board> boardEntities = boardRepository.findByContentContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if(boardEntities.isEmpty()) return boardDtoList;

        for(Board board : boardEntities){
            boardDtoList.add(this.convertEntityToDto(board));;
        }

        return boardDtoList;
    }

    // 2. 제목으로 검색
    @Transactional
    public List<BoardDto> searchT(String keyword){
        List<Board> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if(boardEntities.isEmpty()) return boardDtoList;

        for(Board board : boardEntities){
            boardDtoList.add(this.convertEntityToDto(board));;
        }

        return boardDtoList;
    }

    // 3. 제목 + 내용으로 검색
//    @Transactional
//    public List<BoardDto> searchTc(String keyword){
//        List<Board> boardEntitiesByTitle = boardRepository.findByTitleContaining(keyword);
//        List<Board> boardEntitiesByContent = boardRepository.findByContentContaining(keyword);
//
//        List<Board> combinedBoardEntities = new ArrayList<>();
//        combinedBoardEntities.addAll(boardEntitiesByTitle);
//        combinedBoardEntities.addAll(boardEntitiesByContent);
//
//        List<BoardDto> boardDtoList = new ArrayList<>();
//
//        if(combinedBoardEntities.isEmpty()) return boardDtoList;
//
//        for(Board board : combinedBoardEntities){
//            boardDtoList.add(this.convertEntityToDto(board));;
//        }
//
//        return boardDtoList;
//    }
    @Transactional
    public List<BoardDto> searchTc(String keyword){
        List<Board> boardEntitiesByTitle = boardRepository.findByTitleContaining(keyword);
        List<Board> boardEntitiesByContent = boardRepository.findByContentContaining(keyword);

        // 중복을 제거하고 유일한 항목만 포함하는 Set을 사용
        Set<Board> combinedBoardEntitiesSet = new LinkedHashSet<>();
        combinedBoardEntitiesSet.addAll(boardEntitiesByTitle);
        combinedBoardEntitiesSet.addAll(boardEntitiesByContent);

        // Set을 다시 리스트로 변환하여 순서를 유지
        List<Board> combinedBoardEntities = new ArrayList<>(combinedBoardEntitiesSet);



        List<BoardDto> boardDtoList = new ArrayList<>();

        if(combinedBoardEntities.isEmpty()) return boardDtoList;

        for(Board board : combinedBoardEntities){
            boardDtoList.add(this.convertEntityToDto(board));;
        }

        return boardDtoList;
    }
    @Transactional
    public List<BoardDto> searchW(String keyword) {
        List<Board> boardEntities = boardRepository.findByWriterContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtoList;

        for (Board board : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(board));
            ;
        }

        return boardDtoList;

    }
}