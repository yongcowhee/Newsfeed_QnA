package com.sparta.newsfeed_qna.entity;

import com.sparta.newsfeed_qna.dto.CommentCreateRequestDto;
import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import com.sparta.newsfeed_qna.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long heartCount;


    public Comment(CommentCreateRequestDto dto, User user, Board board) {
        this.board = board;
        this.text = dto.getText();
        this.user = user;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.text = commentRequestDto.getText();
    }

    public void plusLike(){
        this.heartCount += 1;
    }

    public void minusLike(){
        this.heartCount -= 1;
    }
}
