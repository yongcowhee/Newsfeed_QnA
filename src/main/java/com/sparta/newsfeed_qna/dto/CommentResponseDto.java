package com.sparta.newsfeed_qna.dto;

import com.sparta.newsfeed_qna.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;

    private String text;

    private LocalDateTime createDate;

    public CommentResponseDto(Comment savedcomment) {
        this.id = savedcomment.getId();
        this.text = savedcomment.getText();
    }
}
