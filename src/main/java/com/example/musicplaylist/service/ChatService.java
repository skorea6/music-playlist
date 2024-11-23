package com.example.musicplaylist.service;

import com.example.musicplaylist.dto.request.chat.ChatMessageDtoRequest;
import com.example.musicplaylist.dto.response.chat.ChatMessageDtoResponse;
import com.example.musicplaylist.entity.Member;
import com.example.musicplaylist.entity.Playlist;
import com.example.musicplaylist.repository.MemberRepository;
import com.example.musicplaylist.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {
    private final PlaylistRepository playlistRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅 메시지
     */
    public ChatMessageDtoResponse message(String memberUserId, ChatMessageDtoRequest request) {
        Playlist playlist = playlistRepository.findByCode(request.getCode());
        if (playlist == null) {
            throw new IllegalArgumentException("찾을 수 없는 플레이리스트입니다.");
        }

        Member member = memberRepository.findByUserId(memberUserId);
        return new ChatMessageDtoResponse(member.getUserId(), member.getNick(), request.getMessage());
    }
}
