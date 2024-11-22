package com.example.musicplaylist.dto.response.playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistDetailDtoResponse {
    private String code;
    private String name;
    private Boolean isRoomAdmin;
    private String memberUserId;
    private String memberNick;

    public PlaylistDetailDtoResponse(String code, String name, Boolean isRoomAdmin, String memberUserId, String memberNick) {
        this.code = code;
        this.name = name;
        this.isRoomAdmin = isRoomAdmin;
        this.memberUserId = memberUserId;
        this.memberNick = memberNick;
    }
}
