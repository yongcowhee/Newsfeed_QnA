package com.sparta.newsfeed_qna.entity;

import com.sparta.newsfeed_qna.dto.CommentCreateRequestDto;
import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    void update() {
        // given
        CommentCreateRequestDto commentCreateRequestDto = new CommentCreateRequestDto("댓글 생성 테스트");
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setText("댓글 수정 테스트");
        Comment comment = new Comment(commentCreateRequestDto, null, null);

        // when
        comment.update(commentRequestDto);

        // then
        assertEquals("댓글 수정 테스트", comment.getText());
    }
}