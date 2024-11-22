package com.example.musicplaylist.controller.api;

import com.example.musicplaylist.common.dto.BaseResponse;
import com.example.musicplaylist.dto.request.playlist.PlaylistCreateDtoRequest;
import com.example.musicplaylist.dto.request.playlist.PlaylistDeleteDtoRequest;
import com.example.musicplaylist.dto.response.playlist.PlaylistCreateDtoResponse;
import com.example.musicplaylist.service.PlaylistService;
import com.example.musicplaylist.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/playlist")
public class PlaylistApiController {
    private final PlaylistService playlistService;
    private final SecurityService securityService;

    public PlaylistApiController(PlaylistService playlistService, SecurityService securityService) {
        this.playlistService = playlistService;
        this.securityService = securityService;
    }

    @PostMapping("/create")
    public BaseResponse<PlaylistCreateDtoResponse> createPlaylist(@RequestBody @Valid PlaylistCreateDtoRequest request) {
        securityService.checkIsLogined();

        String memberUserId = securityService.getMemberUserId();
        PlaylistCreateDtoResponse response = playlistService.createPlaylist(memberUserId, request);
        return new BaseResponse<>(response);
    }

    /**
     * 관리자만 이용 가능
     */
    @PostMapping("/delete")
    public BaseResponse<Object> deletePlaylist(@RequestBody @Valid PlaylistDeleteDtoRequest request) {
        String resultMsg = playlistService.deletePlaylist(request);
        return new BaseResponse<>(resultMsg);
    }
}
