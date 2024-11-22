package com.example.musicplaylist.dto.response.playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistCreateDtoResponse {
    private String code;

    public PlaylistCreateDtoResponse(String code) {
        this.code = code;
    }
}
