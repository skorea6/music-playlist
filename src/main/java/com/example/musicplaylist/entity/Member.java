package com.example.musicplaylist.entity;


import com.example.musicplaylist.dto.response.member.MemberDtoResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@ToString(callSuper = true) // auditing 필드까지 호출
@Table(indexes = {
        @Index(columnList = "userId", unique = true),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "nick", unique = true),
        @Index(columnList = "createdBy")
})
@Entity
public class Member extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nick;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "member", cascade = CascadeType.ALL, targetEntity = MemberRole.class)
    private List<MemberRole> memberRole = new ArrayList<>();

    protected Member() {}

    public Member(String userId, String password, String nick) {
        this.userId = userId;
        this.password = password;
        this.nick = nick;
    }

    public MemberDtoResponse toDto() {
        return new MemberDtoResponse(id, userId, nick);
    }

}
