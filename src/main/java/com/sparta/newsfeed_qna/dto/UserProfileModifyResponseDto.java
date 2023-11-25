package com.sparta.newsfeed_qna.dto;

import com.sparta.newsfeed_qna.entity.User;
import lombok.Getter;

@Getter
public class UserProfileModifyResponseDto {
    private String userName;
    private String userNickName;
    private String userOneLiner;

    public UserProfileModifyResponseDto(User user) {
        this.userName = user.getUserName();
        this.userNickName = user.getUserNickname();
        this.userOneLiner = user.getOneLiner();
    }
}
