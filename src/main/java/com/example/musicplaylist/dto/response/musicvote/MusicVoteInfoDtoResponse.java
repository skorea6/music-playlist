package com.example.musicplaylist.dto.response.musicvote;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicVoteInfoDtoResponse {
    private Long likeCount;
    private Long disLikeCount;

    public MusicVoteInfoDtoResponse(Long likeCount, Long disLikeCount) {
        this.likeCount = likeCount;
        this.disLikeCount = disLikeCount;
    }
}
