package com.sparta.newsfeed_qna.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardRequestDto {
    private Long id;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
