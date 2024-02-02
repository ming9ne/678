package com.sw678.crud.chat.controller;

import com.sw678.crud.chat.model.Chatting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/chat")
    public String chatting(Model model){

        Chatting chatting = new Chatting("Hello, Chat!");

        model.addAttribute("chatting", chatting);

        return "chat/chatView";
    }
}
