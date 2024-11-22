package com.example.musicplaylist.dto.request.music;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicUpdateSyncDtoRequest {
    @NotNull(message = "id는 필수 입력 값입니다.")
    private Long id;

    @NotNull(message = "시간은 필수 입력 값입니다.")
    private Long time;
}
