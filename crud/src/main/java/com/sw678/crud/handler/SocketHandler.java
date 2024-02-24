package com.sw678.crud.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw678.crud.model.dto.ChatMessage;
import com.sw678.crud.model.dto.ChatRoom;
import com.sw678.crud.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class SocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); // 웹소켓 세션을 담아둘 맵

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        Set<WebSocketSession> sessions=room.getSessions();   //방에 있는 현재 사용자 한명이 WebsocketSession
        System.out.println("chatMessage = " + chatMessage.getSender() + ": " + chatMessage.getMessage());
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            //사용자가 방에 입장하면  Enter메세지를 보내도록 해놓음.  이건 새로운사용자가 socket 연결한 것이랑은 다름.
            //socket연결은 이 메세지 보내기전에 이미 되어있는 상태
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(chatMessage)) );
        }else if (chatMessage.getType().equals(ChatMessage.MessageType.QUIT)) {
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다..");
            sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(chatMessage)) );
        }else {
            sendToEachSocket(sessions,message ); //입장,퇴장 아닐 때는 클라이언트로부터 온 메세지 그대로 전달.
        }
    }
    private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message){
        sessions.parallelStream().forEach( roomSession -> {
            try {
                roomSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 소켓 연결
        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(),session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 소켓 종료
        sessionMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }
}