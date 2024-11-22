package com.example.musicplaylist.repository;

import com.example.musicplaylist.entity.Member;
import com.example.musicplaylist.entity.Music;
import com.example.musicplaylist.entity.MusicVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicVoteRepository extends JpaRepository<MusicVote, Long>{
    MusicVote findByMemberAndMusic(Member member, Music music);
}
