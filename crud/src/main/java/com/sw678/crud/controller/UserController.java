package com.sw678.crud.controller;

import com.sw678.crud.model.dto.UserDto;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("userDto",new UserDto());

        return "/user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDto userDto, HttpServletRequest request, Model model) throws Exception{
        HttpSession session = request.getSession();
        UserDto findUserDto = userService.login(userDto);
        if(findUserDto != null){
            session.setAttribute("user",findUserDto);
        }else{
            model.addAttribute("message","ID나 비밀번호가 다릅니다.");
            return "/user/login";
        }
        return "redirect:/user";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        request.getSession(true);
        return "redirect:/user";
    }

    // 회원가입
    @GetMapping("/signup")
    public String signUp(UserDto dto){

        return "/user/signup";
    }


    @PostMapping("/signup")
    public String SignUpPost(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            return "user/signup";
        } else{
            if(userService.userCheck(userDto)){
                userService.join(userDto);
            }
        }

        return "redirect:/user/login";
    }
/*
    @PostMapping("/check")
    @ResponseBody
    public int idCheck(@ModelAttribute UserDto userDto){
        int count = userService.userCheck(userDto.getUsername());
        return count;
    }

*/
}
