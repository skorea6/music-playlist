package com.example.musicplaylist.repository;

import com.example.musicplaylist.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(String userId);
    Member findByNick(String nick);
}
