package com.example.musicplaylist.dto.response.music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicDetailDtoResponse {
    private Long id;
    private String name;
    private String memberUserId;
    private String memberNick;

    private Long voteLikeCount;
    private Long voteDisLikeCount;
    private String youtubeId;
    private Long youtubeTime;
    private Boolean isPlaylist;

    public MusicDetailDtoResponse(Long id, String name, String memberUserId, String memberNick, Long voteLikeCount, Long voteDisLikeCount, String youtubeId, Long youtubeTime, Boolean isPlaylist) {
        this.id = id;
        this.name = name;
        this.memberUserId = memberUserId;
        this.memberNick = memberNick;
        this.voteLikeCount = voteLikeCount;
        this.voteDisLikeCount = voteDisLikeCount;
        this.youtubeId = youtubeId;
        this.youtubeTime = youtubeTime;
        this.isPlaylist = isPlaylist;
    }
}
