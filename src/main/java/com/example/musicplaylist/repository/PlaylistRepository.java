package com.example.musicplaylist.repository;

import com.example.musicplaylist.entity.Member;
import com.example.musicplaylist.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByCode(String code);
    Page<Playlist> findAllByMember(Pageable pageable, Member member);

    @Query("SELECT p FROM Playlist p ORDER BY p.createdAt DESC")
    List<Playlist> findAllWithMusics(Pageable pageable);

    @Query("SELECT p FROM Playlist p WHERE p.member.userId = :userId ORDER BY p.createdAt DESC")
    List<Playlist> findAllMyWithMusics(String userId, Pageable pageable);
}
