package com.sparta.newsfeed_qna.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardRequestDto {
    private String boardTitle;
    private String boardContent;
}
