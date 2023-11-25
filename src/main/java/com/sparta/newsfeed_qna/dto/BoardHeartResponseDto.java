package com.sparta.newsfeed_qna.dto;

import lombok.Getter;

@Getter
public class BoardHeartResponseDto {
    private String boardTitle;
    private String userName;
    private String heartResult;

    public BoardHeartResponseDto(String boardTitle, String userName, String heartResult) {
        this.boardTitle = boardTitle;
        this.userName = userName;
        this.heartResult = heartResult;
    }

    public BoardHeartResponseDto(String heartResult) {
        this.heartResult = heartResult;
    }
}
