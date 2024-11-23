package com.example.musicplaylist.dto.websocket;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage<T> {
    private String type;
    private T data;
}
