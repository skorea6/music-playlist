package com.example.musicplaylist.dto.response.music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicAddDtoResponse {
    private Long id;

    public MusicAddDtoResponse(Long id) {
        this.id = id;
    }
}
