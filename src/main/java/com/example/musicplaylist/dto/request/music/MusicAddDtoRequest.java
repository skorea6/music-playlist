package com.example.musicplaylist.dto.request.music;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicAddDtoRequest {
    @NotBlank(message = "code는 필수 입력 값입니다.")
    private String code;

    @NotBlank(message = "유튜브 링크는 필수 입력 값입니다.")
    private String youtubeLink;
}
