package com.sparta.newsfeed_qna.dto;

import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class BoardResponseDtoTest {
    @Test
    @DisplayName("BoardResponseDto 생성자 테스트")
    void createDto(){
        // given
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto();
        userSignupRequestDto.setUserName("용소희");
        userSignupRequestDto.setPassword("1234");
        userSignupRequestDto.setEmail("ari@gmail.com");

        User user = new User(userSignupRequestDto);

        Board board = new Board(1L, "createDtoTest", "boardCreateDto 테스트 중입니다.", user,null);

        // when
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        // then
        assertEquals(1L, boardResponseDto.getBoardId());
        assertEquals("createDtoTest", boardResponseDto.getBoardTitle());
        assertEquals("용소희", boardResponseDto.getBoardAuthor());
        assertEquals("boardCreateDto 테스트 중입니다.", boardResponseDto.getBoardContent());
        assertEquals(board.getCreatedAt(), boardResponseDto.getCreateAt());
        assertEquals(board.getModifiedAt(), boardResponseDto.getModifiedAt());
    }
}