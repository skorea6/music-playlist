package com.example.musicplaylist.dto.response.playlist;

import com.example.musicplaylist.dto.response.music.MusicDetailDtoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistListDtoResponse {
    private String code;
    private String name;
    private Boolean isRoomAdmin;
    private String memberUserId;
    private String memberNick;
    private List<MusicDetailDtoResponse> musics;

    public PlaylistListDtoResponse(String code, String name, Boolean isRoomAdmin, String memberUserId, String memberNick, List<MusicDetailDtoResponse> musics) {
        this.code = code;
        this.name = name;
        this.isRoomAdmin = isRoomAdmin;
        this.memberUserId = memberUserId;
        this.memberNick = memberNick;
        this.musics = musics;
    }
}
