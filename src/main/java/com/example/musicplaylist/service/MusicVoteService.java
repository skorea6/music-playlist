package com.example.musicplaylist.service;


import com.example.musicplaylist.dto.enums.MusicVoteType;
import com.example.musicplaylist.dto.request.musicvote.MusicVoteUpdateDtoRequest;
import com.example.musicplaylist.dto.response.musicvote.MusicVoteInfoDtoResponse;
import com.example.musicplaylist.dto.response.musicvote.MusicVoteUpdateDtoResponse;
import com.example.musicplaylist.entity.Member;
import com.example.musicplaylist.entity.Music;
import com.example.musicplaylist.entity.MusicVote;
import com.example.musicplaylist.repository.MemberRepository;
import com.example.musicplaylist.repository.MusicRepository;
import com.example.musicplaylist.repository.MusicVoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Transactional
@Service
public class MusicVoteService {
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;
    private final MusicVoteRepository musicVoteRepository;

    public MusicVoteUpdateDtoResponse update(String memberUserId, MusicVoteUpdateDtoRequest request){
        Member member = memberRepository.findByUserId(memberUserId);
        Music music = musicRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 음악입니다.")
        );
        MusicVote musicVote = musicVoteRepository.findByMemberAndMusic(member, music);

        boolean status = false;
        Long likeCount = music.getVoteLikeCount();
        Long disLikeCount = music.getVoteDisLikeCount();

        if(musicVote != null){
            musicVoteRepository.deleteById(musicVote.getId());
            if (musicVote.getMusicVoteType() == MusicVoteType.LIKE) {
                likeCount--;
            }else if(musicVote.getMusicVoteType() == MusicVoteType.DISLIKE) {
                disLikeCount--;
            }
        }

        if(musicVote == null || musicVote.getMusicVoteType() != request.getType()){
            musicVoteRepository.save(new MusicVote(request.getType(), member, music));
            status = true;

            if (request.getType() == MusicVoteType.LIKE) {
                likeCount++;
            }else if(request.getType() == MusicVoteType.DISLIKE) {
                disLikeCount++;
            }
        }

        music.updateVoteCount(likeCount, disLikeCount);
        musicRepository.save(music);

        return new MusicVoteUpdateDtoResponse(
                request.getType().name(),
                status,
                new MusicVoteInfoDtoResponse(likeCount, disLikeCount)
        );
    }
}
