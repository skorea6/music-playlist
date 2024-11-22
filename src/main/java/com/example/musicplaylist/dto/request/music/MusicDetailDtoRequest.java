package com.example.musicplaylist.dto.request.music;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicDetailDtoRequest {
    @NotBlank(message = "code는 필수 입력 값입니다.")
    private String code;
}
