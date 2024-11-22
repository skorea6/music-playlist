package com.example.musicplaylist.dto.request.playlist;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistDetailDtoRequest {
    @NotBlank(message = "code는 필수 입력 값입니다.")
    private String code;

    public PlaylistDetailDtoRequest(String code) {
        this.code = code;
    }
}
