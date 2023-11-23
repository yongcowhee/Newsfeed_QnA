package com.sparta.newsfeed_qna.dto;

import com.sparta.newsfeed_qna.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Long id, String boardTitle, String boardContent, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.boardId = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public BoardResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();

    }
}
