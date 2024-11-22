package com.example.musicplaylist.controller;

import com.example.musicplaylist.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private final SecurityService securityService;

    public IndexController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/")
    public String index(Model model) {
        String memberUserId = securityService.getMemberUserId();

//        List<MapDtoResponse> places = mapService.getAllPlace();
//        List<PostDetailDtoResponse> allPosts = postService.getAllPosts();
//        List<PostDetailDtoResponse> likeTopPosts = postService.getLikeTopPosts();
//
//        List<List<PostDetailDtoResponse>> columnPosts = new ArrayList<>(4);
//        for (int i = 0; i < 4; i++) {
//            columnPosts.add(new ArrayList<>());
//        }
//
//        EventDetailDtoResponse.processEventPosts(allPosts, columnPosts);
//
//        model.addAttribute("places", places);
//        model.addAttribute("allColumnPosts", columnPosts);
//        model.addAttribute("likeTopPosts", likeTopPosts);
//
//        if(memberUserId != null){
//            List<PostDetailDtoResponse> likePosts = postLikeService.list(memberUserId);
//            model.addAttribute("likePosts", likePosts);
//            model.addAttribute("hasLikePosts", true);
//        }

        return "index";
    }
}
