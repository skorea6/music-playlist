package com.example.musicplaylist.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketErrorMessage {
    private String type;       // 메시지 유형 (예: "ERROR")
    private String message;    // 에러 메시지
    private String roomId;     // 발생한 Room ID
}

