package com.sparta.newsfeed_qna.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignupRequestDto {
    @NotBlank(message = "유저 이름은 필수 입력사항입니다.")
    private String userName;

    @NotBlank(message = "닉네임은 필수 입력사항입니다.")
    @Size(min = 3, message = "닉네임은 최소 3자 이상입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "알파벳 대소문자, 숫자만 입력 가능합니다.")
    private String userNickname;

    @NotBlank(message = "")
    private String password;
    private String email;
}
