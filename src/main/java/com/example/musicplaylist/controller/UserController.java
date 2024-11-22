package com.example.musicplaylist.controller;

import com.example.musicplaylist.dto.response.playlist.PlaylistListDtoResponse;
import com.example.musicplaylist.entity.Member;
import com.example.musicplaylist.service.MemberService;
import com.example.musicplaylist.service.PlaylistService;
import com.example.musicplaylist.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {
    private final MemberService memberService;
    private final PlaylistService playlistService;
    private final SecurityService securityService;

    public UserController(MemberService memberService, PlaylistService playlistService, SecurityService securityService) {
        this.memberService = memberService;
        this.playlistService = playlistService;
        this.securityService = securityService;
    }

    @GetMapping("/{userId}")
    public String info(@PathVariable String userId, Model model) {
        String memberUserId = securityService.getMemberUserId();
        Optional<Member> member = memberService.searchUser(userId); // userId null이나 빈칸일 경우?

        if(member.isEmpty()){
            return "redirect:/";
        }

        List<PlaylistListDtoResponse> playlistList = playlistService.myList(memberUserId);

        model.addAttribute("memberInfo", member.get().toDto());
        model.addAttribute("myPlaylists", playlistList);

        if(memberUserId != null && memberUserId.equals(userId)){
            model.addAttribute("isMyProfile", true);
        }

        return "member/info";
    }

}
