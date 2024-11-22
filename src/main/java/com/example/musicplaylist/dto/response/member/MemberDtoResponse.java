package com.example.musicplaylist.dto.response.member;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDtoResponse {
    private Long id;
    private String userId;
    private String nick;

    public MemberDtoResponse(Long id, String userId, String nick) {
        this.id = id;
        this.userId = userId;
        this.nick = nick;
    }
}
