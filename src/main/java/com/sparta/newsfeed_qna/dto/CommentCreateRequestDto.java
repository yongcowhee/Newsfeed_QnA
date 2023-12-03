package com.sparta.newsfeed_qna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CommentCreateRequestDto {
    private String text;
}
