package com.example.musicplaylist.dto.response.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDtoResponse {
    private String memberUserId;
    private String memberNick;
    private String message;

    public ChatMessageDtoResponse(String memberUserId, String memberNick, String message) {
        this.memberUserId = memberUserId;
        this.memberNick = memberNick;
        this.message = message;
    }
}
