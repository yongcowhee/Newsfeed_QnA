package com.sparta.newsfeed_qna.dto;

import com.sparta.newsfeed_qna.entity.Comment;
import com.sparta.newsfeed_qna.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long boardId;
    private Long commentId;
    private String commentAuthor;

    private String text;

    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment) {
        this.boardId = comment.getBoard().getBoardId();
        this.commentId = comment.getCommentId();
        this.commentAuthor = comment.getUser().getUserName();
        this.text = comment.getText();
        this.createDate = comment.getCreatedAt();
    }
}
