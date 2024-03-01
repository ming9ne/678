package com.sw678.crud.controller;

import com.sw678.crud.model.dto.SignupDto;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.repository.UserRepository;
import com.sw678.crud.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
public class UserController {

    private final UserService userService;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "sangjuncho232@gmail.com";

    @Autowired
    private UserRepository userRepository;


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

    @GetMapping("loginSuccess")
    public String LoginSuccess(HttpServletRequest request, Model model){
        model.addAttribute("user", request.getAttribute("user"));
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
        userService.signup(signupDto);

        return "redirect:/login";
    }

    //////////////////////////////////////
    //// MAIL

    @ResponseBody
    @GetMapping("/signup/emailAuth")
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

    // my page

    @GetMapping("/myPage")
    public String myPage(Model model, @AuthenticationPrincipal UserDetail user) {
        SignupDto userDetail = userService.getCurrentUserDetails();

        // 모델에 사용자 정보 추가
        model.addAttribute("userDetail", userDetail);
        model.addAttribute("user", user.getUser().getNickname());

        return "myPage";
    }

    //nickname




}