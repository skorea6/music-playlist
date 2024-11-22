package com.example.musicplaylist.controller.api;

import com.example.musicplaylist.common.dto.BaseResponse;
import com.example.musicplaylist.dto.request.musicvote.MusicVoteUpdateDtoRequest;
import com.example.musicplaylist.dto.response.musicvote.MusicVoteUpdateDtoResponse;
import com.example.musicplaylist.service.MusicVoteService;
import com.example.musicplaylist.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/music/vote")
public class MusicVoteApiController {
    private final MusicVoteService musicVoteService;
    private final SecurityService securityService;

    public MusicVoteApiController(MusicVoteService musicVoteService, SecurityService securityService) {
        this.musicVoteService = musicVoteService;
        this.securityService = securityService;
    }

    @PostMapping("/update")
    public BaseResponse<MusicVoteUpdateDtoResponse> update(@RequestBody @Valid MusicVoteUpdateDtoRequest request){
        securityService.checkIsLogined();

        String memberUserId = securityService.getMemberUserId();
        MusicVoteUpdateDtoResponse response = musicVoteService.update(memberUserId, request);
        return new BaseResponse<>(response);
    }
}
