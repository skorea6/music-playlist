package com.example.musicplaylist.controller;

import com.example.musicplaylist.dto.request.music.MusicDetailDtoRequest;
import com.example.musicplaylist.dto.request.playlist.PlaylistDetailDtoRequest;
import com.example.musicplaylist.dto.response.music.MusicDetailDtoResponse;
import com.example.musicplaylist.dto.response.playlist.PlaylistDetailDtoResponse;
import com.example.musicplaylist.service.MusicService;
import com.example.musicplaylist.service.PlaylistService;
import com.example.musicplaylist.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller("/playlist")
public class PlaylistController {
    private final SecurityService securityService;
    private final PlaylistService playlistService;
    private final MusicService musicService;

    public PlaylistController(SecurityService securityService, PlaylistService playlistService, MusicService musicService) {
        this.securityService = securityService;
        this.playlistService = playlistService;
        this.musicService = musicService;
    }

    @GetMapping("/{code}")
    public String playlist(@PathVariable String code, Model model) {
        String memberUserId = securityService.getMemberUserId();

        PlaylistDetailDtoResponse playlistDetail = playlistService.detail(memberUserId, new PlaylistDetailDtoRequest(code));
        List<MusicDetailDtoResponse> listMusic = musicService.listMusic(new MusicDetailDtoRequest(code));
        MusicDetailDtoResponse votingMusic = musicService.votingMusic(new MusicDetailDtoRequest(code));

        model.addAttribute("playlistDetail", playlistDetail);
        model.addAttribute("listMusic", listMusic);
        model.addAttribute("votingMusic", votingMusic);

        return "playlist/index";
    }
}
