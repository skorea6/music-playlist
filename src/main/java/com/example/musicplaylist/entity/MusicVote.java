package com.example.musicplaylist.entity;

import com.example.musicplaylist.dto.enums.MusicVoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true) // auditing 필드까지 호출
@Table(indexes = {
        @Index(columnList = "createdBy")
})
@Entity
public class MusicVote extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private MusicVoteType musicVoteType = null; // LIKE, DISLIKE 구분

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_music_vote_member_id"))
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_music_vote_music_id"))
    @ToString.Exclude
    private Music music;

    public MusicVote() {
    }

    public MusicVote(MusicVoteType musicVoteType, Member member, Music music) {
        this.musicVoteType = musicVoteType;
        this.member = member;
        this.music = music;
    }
}
