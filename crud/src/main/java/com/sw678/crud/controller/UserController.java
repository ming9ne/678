package com.sw678.crud.controller;

import com.sw678.crud.model.dto.SigninDto;
import com.sw678.crud.model.dto.SignupDto;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signin")
    public String signinForm(Model model){
        model.addAttribute("signinDto", new SignupDto());

        return "signinForm";
    }

    @PostMapping("/signin")
    public String signinForm(@Valid SigninDto signinDto, BindingResult bindingResult){

        // 검증 실패시
        if(bindingResult.hasErrors()){
            return "signinForm";
        }

        // 로그인 시도
        User loginUser = userService.login(signinDto);

        if(loginUser == null){
            bindingResult.reject("loginFail", "아이디나 비밀번호가 일치하지 않습니다.");
            return "signinForm";
        }

        System.out.println("로그인 성공입니다.");
        // 로그인 성공 처리시
        return "redirect:/board/list";
    }

    // 회원가입
    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("signupDto", new SignupDto());

        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupDto signupDto, BindingResult bindingResult){

        // 검증 오류 확인
        if(bindingResult.hasErrors()){
            return "signupForm";
        }

        // 회원가입 로직
        User user = signupDto.toEntity();
        userService.signup(user);

        return "redirect:/signin";
    }


}
