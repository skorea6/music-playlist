package com.example.musicplaylist.dto.response.music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicUpdateSyncDtoResponse {
    private String youtubeId;
    private Long youtubeTime;

    public MusicUpdateSyncDtoResponse(String youtubeId, Long youtubeTime) {
        this.youtubeId = youtubeId;
        this.youtubeTime = youtubeTime;
    }
}
