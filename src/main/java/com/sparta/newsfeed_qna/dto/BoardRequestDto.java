package com.sparta.newsfeed_qna.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardRequestDto {
    @Size(max = 500)
    private String boardTitle;
    @Size(max = 5000)
    private String boardContent;
}
