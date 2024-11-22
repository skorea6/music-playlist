package com.example.musicplaylist.service;

import com.example.musicplaylist.common.status.RoleType;
import com.example.musicplaylist.dto.security.SnapPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityService {

    private Object getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public SnapPrincipal getPrincipal() {
        Object principal = getAuthentication();
        if (principal instanceof SnapPrincipal) {
            return ((SnapPrincipal) principal);
        } else {
            return null;
        }
    }


    public String getMemberUserId() {
        SnapPrincipal principal = getPrincipal();
        if (principal != null) {
            return principal.getUsername();
        } else {
            return null;
        }
    }

    public void checkIsLogined(){
        if(getMemberUserId() == null){
            throw new IllegalArgumentException("로그인 후 이용 가능합니다.");
        }
    }

    public Boolean checkHasAdmin(){
        return getAuthorities().contains(RoleType.ADMIN.toString());
    }

    public void checkIsAdmin(){
//        getAuthorities().forEach(System.out::println);
        if(!checkHasAdmin()){
            throw new IllegalArgumentException("관리자만 이용 가능합니다.");
        }
    }

    public List<String> getAuthorities() {
        SnapPrincipal principal = getPrincipal();
        if (principal != null) {
            return principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
