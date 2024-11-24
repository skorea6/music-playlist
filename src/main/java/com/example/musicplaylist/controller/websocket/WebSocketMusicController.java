package com.example.musicplaylist.controller.websocket;

import com.example.musicplaylist.dto.request.chat.ChatMessageDtoRequest;
import com.example.musicplaylist.dto.request.music.MusicAddDtoRequest;
import com.example.musicplaylist.dto.request.music.MusicDeleteDtoRequest;
import com.example.musicplaylist.dto.request.music.MusicUpdateSyncDtoRequest;
import com.example.musicplaylist.dto.request.music.MusicUpdateToPlaylistDtoRequest;
import com.example.musicplaylist.dto.request.musicvote.MusicVoteUpdateDtoRequest;
import com.example.musicplaylist.dto.response.chat.ChatMessageDtoResponse;
import com.example.musicplaylist.dto.response.music.MusicDetailDtoResponse;
import com.example.musicplaylist.dto.response.music.MusicUpdateSyncDtoResponse;
import com.example.musicplaylist.dto.response.musicvote.MusicVoteUpdateDtoResponse;
import com.example.musicplaylist.dto.security.SnapPrincipal;
import com.example.musicplaylist.dto.websocket.WebSocketErrorMessage;
import com.example.musicplaylist.dto.websocket.WebSocketMessage;
import com.example.musicplaylist.service.ChatService;
import com.example.musicplaylist.service.MusicService;
import com.example.musicplaylist.service.MusicVoteService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.concurrent.locks.ReentrantLock;


@Controller
public class WebSocketMusicController {
    private final MusicService musicService;
    private final MusicVoteService musicVoteService;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ReentrantLock musicLock = new ReentrantLock();

    public WebSocketMusicController(MusicService musicService, MusicVoteService musicVoteService, ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.musicService = musicService;
        this.musicVoteService = musicVoteService;
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    // 음악 추가
    @MessageMapping("/music/add")
    public void addMusic(@Payload MusicAddDtoRequest request, StompHeaderAccessor accessor) {
        musicLock.lock();
        try {
            handleWebSocketAction(request.getCode(), accessor, () -> {
                checkIsLogined(accessor);
                String memberUserId = getMemberUserId(accessor);

                MusicDetailDtoResponse response = musicService.addMusic(memberUserId, request);

                WebSocketMessage<MusicDetailDtoResponse> webSocketMessage = new WebSocketMessage<>("ADD_MUSIC", response);
                messagingTemplate.convertAndSend("/topic/room/" + request.getCode(), webSocketMessage);

                sendSystemMessage(request.getCode(), "Successfully Music added!");
            });
        } finally {
            musicLock.unlock();
        }
    }

    // 동기화 업데이트
    @MessageMapping("/music/update-sync")
    public void updateSync(@Payload MusicUpdateSyncDtoRequest request, StompHeaderAccessor accessor) {
        musicLock.lock();
        try {
            String code = musicService.findPlaylistCodeWithMusic(request.getId());
            handleWebSocketAction(code, accessor, () -> {
                checkIsLogined(accessor);
                String memberUserId = getMemberUserId(accessor);

                MusicUpdateSyncDtoResponse response = musicService.updateSync(memberUserId, request);

                WebSocketMessage<MusicUpdateSyncDtoResponse> webSocketMessage = new WebSocketMessage<>("UPDATE_SYNC_MUSIC", response);
                messagingTemplate.convertAndSend("/topic/room/" + code, webSocketMessage);

                sendSystemMessage(code, "Successfully updated music sync!");
            });
        } finally {
            musicLock.unlock();
        }
    }

    // 플레이리스트로 이동
    @MessageMapping("/music/to-playlist")
    public void toPlaylist(@Payload MusicUpdateToPlaylistDtoRequest request, StompHeaderAccessor accessor) {
        musicLock.lock();
        try {
            String code = musicService.findPlaylistCodeWithMusic(request.getId());
            handleWebSocketAction(code, accessor, () -> {
                checkIsLogined(accessor);
                String memberUserId = getMemberUserId(accessor);

                MusicDetailDtoResponse response = musicService.toPlaylist(memberUserId, request);

                WebSocketMessage<MusicDetailDtoResponse> webSocketMessage = new WebSocketMessage<>("TO_PLAYLIST_MUSIC", response);
                messagingTemplate.convertAndSend("/topic/room/" + code, webSocketMessage);

                sendSystemMessage(code, "The music has been moved to a playlist!");
            });
        } finally {
            musicLock.unlock();
        }
    }

    // 음악 삭제
    @MessageMapping("/music/delete")
    public void deleteMusic(@Payload MusicDeleteDtoRequest request, StompHeaderAccessor accessor) {
        musicLock.lock();
        try {
            String code = musicService.findPlaylistCodeWithMusic(request.getId());
            handleWebSocketAction(code, accessor, () -> {
                checkIsLogined(accessor);
                String memberUserId = getMemberUserId(accessor);

                MusicDetailDtoResponse response = musicService.deleteMusic(memberUserId, request);
                WebSocketMessage<MusicDetailDtoResponse> webSocketMessage = new WebSocketMessage<>("DELETE_MUSIC", response);
                messagingTemplate.convertAndSend("/topic/room/" + code, webSocketMessage);

                sendSystemMessage(code, "Music has been removed!");
            });
        } finally {
            musicLock.unlock();
        }
    }

    // 투표 업데이트
    @MessageMapping("/music/vote/update")
    public void updateVote(@Payload MusicVoteUpdateDtoRequest request, StompHeaderAccessor accessor) {
        musicLock.lock();
        try {
            String code = musicService.findPlaylistCodeWithMusic(request.getId());
            handleWebSocketAction(code, accessor, () -> {
                checkIsLogined(accessor);
                String memberUserId = getMemberUserId(accessor);

                MusicVoteUpdateDtoResponse response = musicVoteService.update(memberUserId, request);
                WebSocketMessage<MusicVoteUpdateDtoResponse> webSocketMessage = new WebSocketMessage<>("VOTE_UPDATE_MUSIC", response);
                messagingTemplate.convertAndSend("/topic/room/" + code, webSocketMessage);

                String action = response.getStatus() ? "clicked" : "canceled";
                String type = response.getType().equals("LIKE") ? "LIKE" : "DISLIKE";

                sendSystemMessage(code, "User " + response.getMemberNick()
                        + " " + action + " the " + type + " button!");
            });
        } finally {
            musicLock.unlock();
        }
    }

    // 투표 업데이트
    @MessageMapping("/chat/message")
    public void chatMessage(@Payload ChatMessageDtoRequest request, StompHeaderAccessor accessor) {
        handleWebSocketAction(request.getCode(), accessor, () -> {
            checkIsLogined(accessor);
            String memberUserId = getMemberUserId(accessor);

            ChatMessageDtoResponse response = chatService.message(memberUserId, request);
            WebSocketMessage<ChatMessageDtoResponse> webSocketMessage = new WebSocketMessage<>("CHAT_MESSAGE_USER", response);
            messagingTemplate.convertAndSend("/topic/room/" + request.getCode(), webSocketMessage);
        });
    }


    private Object getAuthentication(StompHeaderAccessor accessor) {
        Authentication authentication = (Authentication) accessor.getSessionAttributes().get("authentication");
        return authentication.getPrincipal();
    }

    private SnapPrincipal getPrincipal(StompHeaderAccessor accessor) {
        Object principal = getAuthentication(accessor);
        if (principal instanceof SnapPrincipal) {
            return ((SnapPrincipal) principal);
        } else {
            return null;
        }
    }

    private String getMemberUserId(StompHeaderAccessor accessor) {
        SnapPrincipal principal = getPrincipal(accessor);
        if (principal != null) {
            return principal.getUsername();
        } else {
            return null;
        }
    }

    private void checkIsLogined(StompHeaderAccessor accessor){
        if(getMemberUserId(accessor) == null){
            throw new IllegalArgumentException("로그인 후 이용 가능합니다.");
        }
    }

    private void sendSystemMessage(String code, String message) {
        WebSocketMessage<String> systemChatMessage = new WebSocketMessage<>("CHAT_MESSAGE_SYSTEM", message);
        messagingTemplate.convertAndSend("/topic/room/" + code, systemChatMessage);
    }

    private void handleWebSocketAction(String roomId, StompHeaderAccessor accessor, Runnable action) {
        try {
            action.run(); // 비즈니스 로직 실행
        } catch (IllegalArgumentException e) {
            WebSocketErrorMessage errorMessage = new WebSocketErrorMessage(
                    "ERROR",
                    e.getMessage(),
                    roomId
            );
            String memberUserId = getMemberUserId(accessor);
            messagingTemplate.convertAndSend("/queue/errors-user-" + memberUserId, errorMessage);
        }
    }

}
