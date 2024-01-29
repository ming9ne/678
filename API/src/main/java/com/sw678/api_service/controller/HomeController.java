package com.sw678.api_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {
    @GetMapping("/home")
    public String Home(){
        return "index";
    }
}
