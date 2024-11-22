package com.example.musicplaylist.repository;

import com.example.musicplaylist.entity.Music;
import com.example.musicplaylist.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long>{
    List<Music> findAllByPlaylistAndIsPlaylistAndIsDeletedOrderByCreatedAtDesc(Playlist playlist, Boolean isPlaylist, Boolean isDeleted);
    Music findByPlaylistAndIsPlaylistAndIsDeleted(Playlist playlist, Boolean isPlaylist, Boolean isDeleted);
}
