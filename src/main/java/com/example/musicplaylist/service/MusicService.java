package com.example.musicplaylist.service;

import com.example.musicplaylist.dto.request.music.*;
import com.example.musicplaylist.dto.response.music.MusicDetailDtoResponse;
import com.example.musicplaylist.dto.response.music.MusicUpdateSyncDtoResponse;
import com.example.musicplaylist.entity.Music;
import com.example.musicplaylist.entity.Playlist;
import com.example.musicplaylist.repository.MemberRepository;
import com.example.musicplaylist.repository.MusicRepository;
import com.example.musicplaylist.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class MusicService {
    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;
    private final MemberRepository memberRepository;

    /**
     * 음악 목록 조회 (isPlaylist = true)
     */
    public List<MusicDetailDtoResponse> listMusic(MusicDetailDtoRequest request) {
        Playlist playlist = playlistRepository.findByCode(request.getCode());
        if (playlist == null) {
            throw new IllegalArgumentException("찾을 수 없는 플레이리스트입니다.");
        }

        List<Music> musicList =
                musicRepository.findAllByPlaylistAndIsPlaylistAndIsDeletedOrderByCreatedAtDesc(playlist, true, false);

        return musicList.stream().map(Music::toDetailDto).collect(Collectors.toList());
    }


    /**
     * 현재 투표중인 음악 조회 (isPlaylist = false)
     */
    public MusicDetailDtoResponse votingMusic(MusicDetailDtoRequest request) {
        Playlist playlist = playlistRepository.findByCode(request.getCode());
        if (playlist == null) {
            throw new IllegalArgumentException("찾을 수 없는 플레이리스트입니다.");
        }

        Music music =
                musicRepository.findByPlaylistAndIsPlaylistAndIsDeleted(playlist, false, false);

        if(music == null){
            return null;
        }

        return music.toDetailDto();
    }

    /**
     * 음악 추가 (with youtube link)
     */
    public MusicDetailDtoResponse addMusic(String memberUserId, MusicAddDtoRequest request) {
        Playlist playlist = playlistRepository.findByCode(request.getCode());
        if (playlist == null) {
            throw new IllegalArgumentException("찾을 수 없는 플레이리스트입니다.");
        }

        String youtubeId = extractVideoId(request.getYoutubeLink());
        if (youtubeId == null) {
            throw new IllegalArgumentException("유효하지 않은 유튜브 링크입니다.");
        }

        if (!Objects.equals(playlist.getMember().getUserId(), memberUserId)) {
            throw new IllegalArgumentException("호스트 권한이 없습니다.");
        }

        Music votingMusic =
                musicRepository.findByPlaylistAndIsPlaylistAndIsDeleted(playlist, false, false);
        if (votingMusic != null) {
            throw new IllegalArgumentException("투표가 진행중인 음악이 있습니다. 처리해주세요.");
        }

        Music duplicateMusic = musicRepository.findByPlaylistAndYoutubeIdAndIsPlaylistAndIsDeleted(playlist, youtubeId, true, false);
        if (duplicateMusic != null) {
            throw new IllegalArgumentException("이미 등록된 음악입니다.");
        }

        try {
            Document doc = Jsoup.connect(generateYouTubeUrl(youtubeId, null))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();
            String title = doc.title().replace(" - YouTube", "");
            Music entity = new Music(
                    memberRepository.findByUserId(memberUserId),
                    playlist,
                    title,
                    youtubeId
            );

            Music music = musicRepository.save(entity);
            return music.toDetailDto();
        }catch (Exception e){
            throw new IllegalArgumentException("유튜브 링크에 접속할 수 없습니다.");
        }
    }

    /**
     * 유튜브 영상 싱크 맞추기
     */
    public MusicUpdateSyncDtoResponse updateSync(String memberUserId, MusicUpdateSyncDtoRequest request){
        Music music = musicRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 음악입니다.")
        );

        if (!Objects.equals(music.getMember().getUserId(), memberUserId)) {
            throw new IllegalArgumentException("호스트 권한이 없습니다.");
        }

        music.updateSync(request.getTime());
        return new MusicUpdateSyncDtoResponse(music.getYoutubeId(), music.getYoutubeTime());
    }

    /**
     * 음악을 플레이리스트로 이동
     */
    public MusicDetailDtoResponse toPlaylist(String memberUserId, MusicUpdateToPlaylistDtoRequest request){
        Music music = musicRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 음악입니다.")
        );

        if (!Objects.equals(music.getMember().getUserId(), memberUserId)) {
            throw new IllegalArgumentException("호스트 권한이 없습니다.");
        }

        music.updateToPlaylist();
        return music.toDetailDto();
    }

    /**
     * 음악 삭제
     */
    public MusicDetailDtoResponse deleteMusic(String memberUserId, MusicDeleteDtoRequest request){
        Music music = musicRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 음악입니다.")
        );

        if (!Objects.equals(music.getMember().getUserId(), memberUserId)) {
            throw new IllegalArgumentException("호스트 권한이 없습니다.");
        }

        music.delete();
        return music.toDetailDto();
    }

    /**
     * 음악을 통해 플레이리스트 code 조회
     */
    public String findPlaylistCodeWithMusic(Long id) {
        Music music = musicRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 음악입니다.")
        );

        return music.getPlaylist().getCode();
    }


    public static String extractVideoId(String url) {
        // 유튜브 비디오 ID를 추출하기 위한 정규식
        String regex = "(?:youtube\\.com.*[?&]v=|youtu\\.be/)([^&?/]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            // 정규식 그룹 1에 있는 비디오 ID 반환
            return matcher.group(1);
        }
        return null; // 비디오 ID가 없을 경우 null 반환
    }

    public static String generateYouTubeUrl(String videoId, Integer t) {
        // 기본 URL
        String baseUrl = "https://youtu.be/" + videoId;

        // 시간 값이 제공되었으면 쿼리 파라미터 추가
        if (t != null) {
            baseUrl += "?t=" + t;
        }

        return baseUrl;
    }
}
