package com.example.musicplaylist.entity;

import com.example.musicplaylist.dto.response.playlist.PlaylistDetailDtoResponse;
import com.example.musicplaylist.dto.response.playlist.PlaylistListDtoResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@ToString(callSuper = true) // auditing 필드까지 호출
@Table(indexes = {
        @Index(columnList = "createdBy"),
})
@Entity
public class Playlist extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
//    private Boolean isMaking = true;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_playlist_member_id"))
    @ToString.Exclude
    private Member member;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @OrderBy("createdAt DESC")
    @ToString.Exclude
    private List<Music> musics;

    public Playlist() {

    }

    public Playlist(String code, String name, Member member) {
        this.code = code;
        this.name = name;
        this.member = member;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public PlaylistListDtoResponse toListDto(String loginUserId) {
        return new PlaylistListDtoResponse(
                code, name, Objects.equals(loginUserId, member.getUserId()),
                member.getUserId(), member.getNick(),
                musics.stream()
                    .filter(x -> !x.getIsDeleted())
                    .map(Music::toDetailDto)
                    .collect(Collectors.toList())
        );
    }

    public PlaylistDetailDtoResponse toDetailDto(String loginUserId) {
        return new PlaylistDetailDtoResponse(code, name, Objects.equals(loginUserId, member.getUserId()), member.getUserId(), member.getNick());
    }
}
