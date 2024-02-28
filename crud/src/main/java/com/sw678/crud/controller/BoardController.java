//package com.sw678.crud.controller;
//
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sw678.crud.model.dto.BoardDto;
//import com.sw678.crud.model.dto.CommentDto;
//import com.sw678.crud.model.entity.socialuser.UserDetail;
//import com.sw678.crud.service.BoardService;
//import com.sw678.crud.service.CommentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@Controller
//@RequestMapping("/board")
//@RequiredArgsConstructor
//public class BoardController {
//
//    private final BoardService boardService;
//    private final CommentService commentService;
//
//    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 pageNation을 수행함
//
//    // MainPage
//    @GetMapping({"", "/list"})
//    public String list(Model model, @RequestParam(value = "page",defaultValue = "1") Integer pageNum,
//                       @AuthenticationPrincipal UserDetail user){
//        System.out.println("user = " + user.getUser().getNickname());
//        List<BoardDto> boardList = boardService.getBoardList(pageNum);
//        Integer[] pageList = boardService.getPageList(pageNum);
//
//        model.addAttribute("user", user.getUser().getNickname());
//        model.addAttribute("boardList",boardList);
//        model.addAttribute("pageList", pageList);
//
//        return "board/list";
//    }
//
//    @GetMapping("/myPageList")
//    public ResponseEntity<List<BoardDto>> getBoardList(@RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
//        List<BoardDto> boardList = boardService.getBoardList(pageNum);
//        return new ResponseEntity<>(boardList, HttpStatus.OK);
//    }
//
//    // write
//    @GetMapping("/post")
//    public String write(Model model, @AuthenticationPrincipal UserDetail user){
//        model.addAttribute("user", user.getUser().getNickname());
//        return "board/write";
//    }
//
//    @PostMapping("post")
//    public String write(BoardDto boardDto, @AuthenticationPrincipal UserDetail user){
//        boardService.savePost(boardDto, user.getUser().getId());
//        return "redirect:/board/list";
//    }
//
//    // read
//    @GetMapping("/post/{no}")
//    public String detail(@PathVariable("no") Long no, Model model, @AuthenticationPrincipal UserDetail user){
//        BoardDto boardDto = boardService.getPost(no);
//        List<CommentDto> commentDto = commentService.loadCommentList(no);
//
//        model.addAttribute("user", user.getUser().getNickname());
//        model.addAttribute("boardDto", boardDto);
//        model.addAttribute("comments", commentDto);
//        model.addAttribute("no", no);
//
//
//        return "board/detail";
//    }
//
//    // edit
//    //{no}로 페이지 넘버를 받는다.
//    @GetMapping("/post/edit/{no}")
//    public String edit(@PathVariable("no") Long no, Model model){
//        BoardDto boardDto = boardService.getPost(no);
//
//        model.addAttribute("boardDto", boardDto);
//        return "board/update";
//    }
//
//    //put 메서드를 이용해 게시물 수정한 부분에 대해 적용
//    @PutMapping("/post/edit/{no}")
//    public String update(BoardDto boardDto, @AuthenticationPrincipal UserDetail user){
//        boardService.savePost(boardDto, user.getUser().getId());
//
//        return "redirect:/board/list";
//    }
//
//    // delete
//    @DeleteMapping("/post/{no}")
//    public String delete(@PathVariable("no") Long no){
//        boardService.deletePost(no);
//
//        return "redirect:/board/list";
//    }
//
//    @GetMapping("/map")
//    public String showMap(Model model) {
//        // 여기에서 지도 페이지를 보여주는 로직을 추가할 수 있습니다.
//        return "board/map";
//    }
//
//    @GetMapping("/mainPage")
//    public String showMain(Model model) {
//        // 여기에서 지도 페이지를 보여주는 로직을 추가할 수 있습니다.
//        return "board/mainPage";
//    }
//
//
//}

package com.sw678.crud.controller;



import com.sw678.crud.model.dto.BoardDto;
import com.sw678.crud.model.dto.CommentDto;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.service.BoardService;
import com.sw678.crud.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 pageNation을 수행함

    // MainPage
    @GetMapping({"", "/list"})
    public String list(Model model, @RequestParam(value = "page",defaultValue = "1") Integer pageNum,
                       @AuthenticationPrincipal UserDetail user){
        System.out.println("user = " + user.getUser().getNickname());
        List<BoardDto> boardList = boardService.getBoardList(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("user", user.getUser().getNickname());
        model.addAttribute("boardList",boardList);
        model.addAttribute("pageList", pageList);

        return "board/list";
    }

    // write
    @GetMapping("/post")
    public String write(Model model, @AuthenticationPrincipal UserDetail user){
        model.addAttribute("user", user.getUser().getNickname());
        return "board/write";
    }

    @PostMapping("post")
    public String write(BoardDto boardDto, @AuthenticationPrincipal UserDetail user){
        boardService.savePost(boardDto, user.getUser().getId());
        return "redirect:/board/list";
    }

    // read
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model, @AuthenticationPrincipal UserDetail user){
        BoardDto boardDto = boardService.getPost(no);
        List<CommentDto> commentDto = commentService.loadCommentList(no);

        model.addAttribute("user", user.getUser().getNickname());
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("comments", commentDto);
        model.addAttribute("no", no);


        return "board/detail";
    }

    // edit
    //{no}로 페이지 넘버를 받는다.
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model){
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);
        return "board/update";
    }

    //put 메서드를 이용해 게시물 수정한 부분에 대해 적용
    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDto, @AuthenticationPrincipal UserDetail user){
        boardService.savePost(boardDto, user.getUser().getId());

        return "redirect:/board/list";
    }

    // delete
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no){
        boardService.deletePost(no);

        return "redirect:/board/list";
    }

    @GetMapping("/map")
    public String showMap(Model model) {
        // 여기에서 지도 페이지를 보여주는 로직을 추가할 수 있습니다.
        return "board/map";
    }

    @GetMapping("/mainPage")
    public String showMain(Model model) {
        // 여기에서 지도 페이지를 보여주는 로직을 추가할 수 있습니다.
        return "board/mainPage";
    }

    // search
    // 1. 제목 + 내용으로 검색
    @GetMapping("/search/complex")
    public String searchComplex(@RequestParam(value = "keyword") String keyword, Model model){
        List<BoardDto> boardDtoList = boardService.searchTc(keyword);

        model.addAttribute("boardList",boardDtoList);

        return "board/list";
    }

    // 2. 제목으로 검색
    @GetMapping("/search/title")
    public String searchTitle(@RequestParam(value = "keyword") String keyword, Model model){
        List<BoardDto> boardDtoList = boardService.searchT(keyword);

        model.addAttribute("boardList",boardDtoList);

        return "board/list";
    }

    // 3. 내용으로 검색
    @GetMapping("/search/content")
    public String searchContent(@RequestParam(value = "keyword") String keyword, Model model){
        List<BoardDto> boardDtoList = boardService.searchC(keyword);

        model.addAttribute("boardList",boardDtoList);

        return "board/list";
    }

}