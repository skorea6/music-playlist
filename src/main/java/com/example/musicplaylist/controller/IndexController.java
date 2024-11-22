package com.example.musicplaylist.controller;

import com.example.musicplaylist.dto.response.playlist.PlaylistListDtoResponse;
import com.example.musicplaylist.service.PlaylistService;
import com.example.musicplaylist.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    private final SecurityService securityService;
    private final PlaylistService playlistService;

    public IndexController(SecurityService securityService, PlaylistService playlistService) {
        this.securityService = securityService;
        this.playlistService = playlistService;
    }

    @GetMapping("/")
    public String index(Model model) {
        String memberUserId = securityService.getMemberUserId();

        List<PlaylistListDtoResponse> playlistList = playlistService.list(memberUserId);
        model.addAttribute("playlists", playlistList);

        return "index";
    }
}
