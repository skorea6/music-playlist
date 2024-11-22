package com.example.musicplaylist.entity;

import com.example.musicplaylist.dto.response.music.MusicDetailDtoResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "createdBy")
})
@Entity
public class Music extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_music_member_id"))
    @ToString.Exclude
    @Setter
    private Member member; // 생성자 (방장)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", foreignKey = @ForeignKey(name = "fk_music_playlist_id"))
    @ToString.Exclude
    @Setter
    private Playlist playlist;

    @Column(nullable = false)
    private String name; // 음악 제목

    @Column(nullable = false)
    private Long voteLikeCount = 0L; // 투표 - 찬성표

    @Column(nullable = false)
    private Long voteDisLikeCount = 0L; // 투표 - 반대표

    @Column(nullable = false)
    private String youtubeId; // 유튜브 비디오 ID

    @Column
    private Long youtubeTime; // 유튜브 영상 싱크

    @Column(nullable = false)
    private Boolean isPlaylist = false; // 현재 플레이리스트에 등록되었는지 여부

    @Column(nullable = false)
    private Boolean isDeleted = false;

    public Music() {

    }

    public Music(Member member, Playlist playlist, String name, String youtubeId) {
        this.member = member;
        this.playlist = playlist;
        this.name = name;
        this.youtubeId = youtubeId;
    }

    public void updateSync(Long youtubeTime) {
        this.youtubeTime = youtubeTime;
    }

    public void updateToPlaylist() {
        this.isPlaylist = true;
    }

    public void updateVoteCount(Long voteLikeCount, Long voteDisLikeCount) {
        this.voteLikeCount = voteLikeCount;
        this.voteDisLikeCount = voteDisLikeCount;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public MusicDetailDtoResponse toDetailDto() {
        return new MusicDetailDtoResponse(
                id, name, member.getUserId(), member.getNick(),
                voteLikeCount, voteDisLikeCount, youtubeId, youtubeTime, isPlaylist
        );
    }
}
