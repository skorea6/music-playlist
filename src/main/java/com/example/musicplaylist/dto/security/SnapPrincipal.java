package com.example.musicplaylist.dto.security;

import com.example.musicplaylist.common.status.RoleType;
import com.example.musicplaylist.entity.Member;
import com.example.musicplaylist.entity.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record SnapPrincipal(
        String userId,
        String password,
        String nick,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

//    public static SnapPrincipal of(String userId, String password, String nick, String name, String email, String department) {
//        // 지금은 인증만 하고 권한을 다루고 있지 않아서 임의로 세팅한다.
//        return SnapPrincipal.of(userId, password, nick, name, email, department, Map.of());
//    }

//    public static SnapPrincipal of(String userId){
//        return SnapPrincipal.of(userId);
//    }

    public static SnapPrincipal of(String userId, String password, String nick, Set<RoleType> roleTypes) {
        //Set<RoleType> roleTypes = Set.of(RoleType.MEMBER);

        return new SnapPrincipal(
                userId,
                password,
                nick,
                roleTypes.stream()
                        .map(RoleType::name)
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static SnapPrincipal from(Member member) {
        return SnapPrincipal.of(
                member.getUserId(),
                member.getPassword(),
                member.getNick(),
                member.getMemberRole().stream().map(MemberRole::getRoleType).collect(Collectors.toSet())
        );
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String nick() {
        return nick;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
