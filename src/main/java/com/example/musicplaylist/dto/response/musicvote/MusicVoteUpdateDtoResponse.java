package com.example.musicplaylist.dto.response.musicvote;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicVoteUpdateDtoResponse {
    private String memberUserId;
    private String memberNick;
    private String type;
    private Boolean status;
    private MusicVoteInfoDtoResponse info;

    public MusicVoteUpdateDtoResponse(String memberUserId, String memberNick, String type, Boolean status, MusicVoteInfoDtoResponse info) {
        this.memberUserId = memberUserId;
        this.memberNick = memberNick;
        this.type = type;
        this.status = status;
        this.info = info;
    }
}
