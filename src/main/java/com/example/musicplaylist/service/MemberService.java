package com.example.musicplaylist.service;

import com.example.musicplaylist.common.status.RoleType;
import com.example.musicplaylist.dto.request.member.MemberSignUpDtoRequest;
import com.example.musicplaylist.entity.Member;
import com.example.musicplaylist.entity.MemberRole;
import com.example.musicplaylist.repository.MemberRepository;
import com.example.musicplaylist.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<Member> searchUser(String username){
        return Optional.ofNullable(memberRepository.findByUserId(username)); // .map(MemberDto::from)
    }

    /**
     * 회원가입
     */
    public void signUp(MemberSignUpDtoRequest request) {
        checkDuplicateUserId(request.getUserId()); // userId 중복 검사
        checkDuplicateNick(request.getNick()); // nick 중복 검사

        Member member = request.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberRepository.save(member);
        memberRoleRepository.save(new MemberRole(RoleType.MEMBER, member));
    }

    private void checkDuplicateUserId(String userId) {
        Member findMember = memberRepository.findByUserId(userId);
        if (findMember != null) {
            throw new IllegalArgumentException("이미 등록된 아이디입니다.");
        }
    }

    private void checkDuplicateNick(String nick) {
        Member findMember = memberRepository.findByNick(nick);
        if (findMember != null) {
            throw new IllegalArgumentException("이미 등록된 닉네임입니다.");
        }
    }
}
