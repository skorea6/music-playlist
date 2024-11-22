package com.example.musicplaylist.controller.api;

import com.example.musicplaylist.common.dto.BaseResponse;
import com.example.musicplaylist.dto.request.music.MusicAddDtoRequest;
import com.example.musicplaylist.dto.request.music.MusicDeleteDtoRequest;
import com.example.musicplaylist.dto.request.music.MusicUpdateSyncDtoRequest;
import com.example.musicplaylist.dto.request.music.MusicUpdateToPlaylistDtoRequest;
import com.example.musicplaylist.dto.response.music.MusicAddDtoResponse;
import com.example.musicplaylist.service.MusicService;
import com.example.musicplaylist.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/music")
public class MusicApiController {
    private final MusicService musicService;
    private final SecurityService securityService;

    public MusicApiController(MusicService musicService, SecurityService securityService) {
        this.musicService = musicService;
        this.securityService = securityService;
    }

    @PostMapping("/add")
    public BaseResponse<MusicAddDtoResponse> addMusic(@RequestBody @Valid MusicAddDtoRequest request) {
        securityService.checkIsLogined();
        String memberUserId = securityService.getMemberUserId();

        MusicAddDtoResponse response = musicService.addMusic(memberUserId, request);
        return new BaseResponse<>(response);
    }

    @PostMapping("/update-sync")
    public BaseResponse<Object> updateSync(@RequestBody @Valid MusicUpdateSyncDtoRequest request) {
        securityService.checkIsLogined();
        String memberUserId = securityService.getMemberUserId();

        String resultMsg = musicService.updateSync(memberUserId, request);
        return new BaseResponse<>(resultMsg);
    }

    @PostMapping("/to-playlist")
    public BaseResponse<Object> toPlaylist(@RequestBody @Valid MusicUpdateToPlaylistDtoRequest request) {
        securityService.checkIsLogined();
        String memberUserId = securityService.getMemberUserId();

        String resultMsg = musicService.toPlaylist(memberUserId, request);
        return new BaseResponse<>(resultMsg);
    }

    @PostMapping("/delete")
    public BaseResponse<Object> deleteMusic(@RequestBody @Valid MusicDeleteDtoRequest request) {
        securityService.checkIsLogined();
        String memberUserId = securityService.getMemberUserId();

        String resultMsg = musicService.deleteMusic(memberUserId, request);
        return new BaseResponse<>(resultMsg);
    }
}
