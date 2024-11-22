package com.example.snapcampus.dto.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpVerificationSendEmailDtoRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@gachon\\.ac\\.kr$", message = "이메일은 @gachon.ac.kr로 끝나야 합니다.")
    private String email;
}
