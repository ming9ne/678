package com.sw678.crud.controller;

import com.sw678.crud.model.dto.ChatRoom;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/enterRoom/{roomId}")
    public String chatRoom(@PathVariable("roomId")Long roomId, Model model, @AuthenticationPrincipal UserDetail user){
        ChatRoom room = chatService.findRoomById(roomId);
        if(room == null)
            room = chatService.createRoom(roomId, user.getUser().getNickname());

        model.addAttribute("sender", user.getUser().getNickname());
        model.addAttribute("room",room);
//        return "chat/view";
        return "chat/view";
    }
}