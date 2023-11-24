package com.sparta.newsfeed_qna.dto;

import lombok.Getter;

@Getter
public class UserProfileModifyRequestDto {
    private String userNickName;
    private String userOneLiner;
    private String password;
}
