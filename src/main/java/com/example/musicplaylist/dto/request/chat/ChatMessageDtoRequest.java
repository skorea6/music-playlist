package com.example.musicplaylist.dto.request.chat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDtoRequest {
    @NotBlank(message = "code는 필수 입력 값입니다.")
    private String code;

    @NotBlank(message = "메시지는 필수 입력 값입니다.")
    private String message;
}
