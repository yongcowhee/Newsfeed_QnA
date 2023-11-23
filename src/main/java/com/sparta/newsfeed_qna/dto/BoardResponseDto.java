package com.sparta.newsfeed_qna.dto;

import com.sparta.newsfeed_qna.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private String boardAuthor;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;


    public BoardResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.boardAuthor = board.getUser().getUserName();
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();

    }
}
