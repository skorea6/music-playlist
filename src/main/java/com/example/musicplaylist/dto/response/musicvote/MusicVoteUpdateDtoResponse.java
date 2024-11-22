package com.example.musicplaylist.dto.response.musicvote;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicVoteUpdateDtoResponse {
    private String type;
    private boolean status;
    private MusicVoteInfoDtoResponse info;

    public MusicVoteUpdateDtoResponse(String type, boolean status, MusicVoteInfoDtoResponse info) {
        this.type = type;
        this.status = status;
        this.info = info;
    }
}
