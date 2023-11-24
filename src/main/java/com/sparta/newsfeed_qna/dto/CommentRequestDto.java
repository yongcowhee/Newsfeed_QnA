package com.sparta.newsfeed_qna.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long commentId;
    private String text;
}
