package com.example.musicplaylist.entity;

import com.example.musicplaylist.common.status.RoleType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_member_role_member_id"))
    private Member member;

    public MemberRole(RoleType roleType, Member member) {
        this.roleType = roleType;
        this.member = member;
    }

    public MemberRole() {

    }
}
