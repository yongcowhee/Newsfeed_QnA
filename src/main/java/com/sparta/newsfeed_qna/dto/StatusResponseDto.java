package com.sparta.newsfeed_qna.dto;

import lombok.Data;

@Data
public class StatusResponseDto {
    private final String message;
    private final int status;

    public StatusResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}