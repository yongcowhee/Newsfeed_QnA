package com.sparta.newsfeed_qna.entity;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @DisplayName("Board Update Test")
    @Test
    void update() {
        // given
        Board board = new Board(2l, "수정 전", "수정 전 내용", null, null);
        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setBoardContent("수정 후 내용");
        boardRequestDto.setBoardTitle("수정 후");

        // when
        board.update(boardRequestDto);

        // then
        assertEquals("수정 후", board.getBoardTitle());
        assertEquals("수정 후 내용", board.getBoardContent());
    }
}