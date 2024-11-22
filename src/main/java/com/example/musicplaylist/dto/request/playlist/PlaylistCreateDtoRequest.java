package com.example.musicplaylist.dto.request.playlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistCreateDtoRequest {
    @NotBlank(message = "플레이리스트 이름은 필수 입력 값입니다.")
    @Size(max = 20, message = "플레이리스트 이름은 최대 20자여야 합니다.")
    private String name;
}
