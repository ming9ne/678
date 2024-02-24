package com.sw678.crud.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    private Long roomId;
    private String name;
    @JsonIgnore
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(Long roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }
}
