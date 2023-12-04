package com.sparta.newsfeed_qna.dto;

import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.Comment;
import com.sparta.newsfeed_qna.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentResponseDtoTest {
    @Test
    @DisplayName("CommentResponseDto 테스트")
    void createComment() {
        // given
        CommentCreateRequestDto commentCreateRequestDto = new CommentCreateRequestDto("댓글 생성 테스트");

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto();
        userSignupRequestDto.setUserName("용소희");
        userSignupRequestDto.setPassword("1234");
        userSignupRequestDto.setEmail("ari@gmail.com");

        User user = new User(userSignupRequestDto);

        Board board = new Board(1L, "createDtoTest", "boardCreateDto 테스트 중입니다.", user,null);

        // when
        Comment comment = new Comment(commentCreateRequestDto, user, board);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        // then
        assertEquals(1L, commentResponseDto.getBoardId());
        assertEquals("용소희", commentResponseDto.getCommentAuthor());
        assertEquals("댓글 생성 테스트", commentResponseDto.getText());
    }
}