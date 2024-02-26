package com.sw678.crud.controller;

import com.sw678.crud.model.dto.SigninDto;
import com.sw678.crud.model.dto.SignupDto;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class UserController {

    private final UserService userService;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "sangjuncho232@gmail.com";

    @Autowired
    public UserController(UserService userService, JavaMailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @GetMapping("/login")
    public String signinForm(Model model){
        model.addAttribute("signinDto", new SignupDto());

        return "loginForm";
    }

    // UserController.java


    @GetMapping("/myPage")
    public String myPage(Model model) {
        SignupDto userDetail = userService.getCurrentUserDetails();

        // 모델에 사용자 정보 추가
        model.addAttribute("userDetail", userDetail);

        return "myPage";
    }



    @PostMapping("/login")
    public String loginForm(@Valid SigninDto signinDto, BindingResult bindingResult){

        // 검증 실패시
        if(bindingResult.hasErrors()){
            return "loginForm";
        }

        // 로그인 시도
        User loginUser = userService.login(signinDto);
        System.out.println(loginUser);

        if(loginUser == null){
            bindingResult.reject("loginFail", "아이디나 비밀번호가 일치하지 않습니다.");
            return "loginForm";
        }

        System.out.println("로그인 성공입니다.");
        // 로그인 성공 처리시
        return "redirect:/board/mainPage";
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
        userService.signup(signupDto);

        return "redirect:/login";
    }

    //////////////////////////////////////
    //// MAIL

    @ResponseBody
    @GetMapping("/emailAuth")
    public String emailAuth(String email){
        Random random = new Random();
        int check = (int)(Math.random() * 899999) + 100000;


        try{
            SimpleMailMessage message = createMessageForm(email, check);
            mailSender.send(message);
        }catch (Exception e){
            System.out.println("에러 : " + e.getMessage());
        }

        return Integer.toString(check);
    }

    private SimpleMailMessage createMessageForm(String email, int check) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();

        String sender = FROM_ADDRESS;
        String receiver = email;
        String title = "678서비스 회원가입 인증 메일";
        String content = "안녕하세요 678 서비스입니다.\n\n"
                + "인증번호 : " + check
                + "\n\n678페이지로 돌아가 해당 인증번호를 기입하여 주세요.";

        message.setFrom(sender);
        message.setTo(receiver);
        message.setSubject(title);
        message.setText(content);

        return message;
    }

    /////////////
    ///////////////////////////////
}