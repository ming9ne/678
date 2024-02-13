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
    public String write(){
        return "board/write";
    }

    @PostMapping("post")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);
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
    public String update(BoardDto boardDto){
        boardService.savePost(boardDto);

        return "redirect:/board/list";
    }

    // delete
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no){
        boardService.deletePost(no);

        return "redirect:/board/list";
    }

}
