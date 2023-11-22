package com.sparta.newsfeed_qna.dto;

import lombok.Data;

@Data
public class UserSignupRequestDTO {
    private String userName;
    private String password;
    private String email;
}
