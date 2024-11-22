package com.example.snapcampus.dto.request.member;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
