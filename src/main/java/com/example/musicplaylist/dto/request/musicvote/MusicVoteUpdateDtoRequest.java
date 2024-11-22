package com.example.musicplaylist.dto.request.musicvote;

import com.example.musicplaylist.dto.enums.MusicVoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicVoteUpdateDtoRequest {
    @NotNull(message = "id는 필수 입력 값입니다.")
    private Long id;

    private MusicVoteType type;
}
