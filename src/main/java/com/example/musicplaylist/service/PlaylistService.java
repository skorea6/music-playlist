package com.example.musicplaylist.service;

import com.example.musicplaylist.dto.request.playlist.PlaylistCreateDtoRequest;
import com.example.musicplaylist.dto.request.playlist.PlaylistDeleteDtoRequest;
import com.example.musicplaylist.dto.request.playlist.PlaylistDetailDtoRequest;
import com.example.musicplaylist.dto.response.playlist.PlaylistCreateDtoResponse;
import com.example.musicplaylist.dto.response.playlist.PlaylistDetailDtoResponse;
import com.example.musicplaylist.dto.response.playlist.PlaylistListDtoResponse;
import com.example.musicplaylist.entity.Playlist;
import com.example.musicplaylist.repository.MemberRepository;
import com.example.musicplaylist.repository.PlaylistRepository;
import com.example.musicplaylist.util.RandomUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final MemberRepository memberRepository;

    public List<PlaylistListDtoResponse> list(String memberUserId) {
        List<Playlist> playlists = playlistRepository.findAllWithMusics(PageRequest.of(0, 100));

        return playlists.stream()
                .filter(music -> !music.getIsDeleted())
                .map(x -> x.toListDto(memberUserId)).collect(Collectors.toList());
    }

    public List<PlaylistListDtoResponse> myList(String memberUserId) {
        List<Playlist> playlists = playlistRepository.findAllMyWithMusics(memberUserId, PageRequest.of(0, 100));

        return playlists.stream()
                .filter(music -> !music.getIsDeleted())
                .map(x -> x.toListDto(memberUserId)).collect(Collectors.toList());
    }

    public PlaylistDetailDtoResponse detail(String memberUserId, PlaylistDetailDtoRequest request) {
        Playlist playlist = playlistRepository.findByCode(request.getCode());

        if (playlist == null) {
            throw new IllegalArgumentException("찾을 수 없는 플레이리스트입니다.");
        }

        return playlist.toDetailDto(memberUserId);
    }

    public PlaylistCreateDtoResponse createPlaylist(String memberUserId, PlaylistCreateDtoRequest playlistCreateDtoRequest){
        String code = RandomUtil.generateRandomString(15);
        Playlist entity = new Playlist(
                code,
                playlistCreateDtoRequest.getName(),
                memberRepository.findByUserId(memberUserId)
        );

        playlistRepository.save(entity);
        return new PlaylistCreateDtoResponse(code);
    }

    public String deletePlaylist(PlaylistDeleteDtoRequest playlistDeleteDtoRequest){
        Playlist playlist = playlistRepository.findByCode(playlistDeleteDtoRequest.getCode());

        if (playlist == null) {
            throw new IllegalArgumentException("찾을 수 없는 플레이리스트입니다.");
        }

        playlist.delete();
        return "플레이리스트가 삭제되었습니다.";
    }
}
