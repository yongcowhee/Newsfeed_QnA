package com.sparta.newsfeed_qna.entity;

import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    // 연관관계 메서드
    public void setUser(User user) {
        this.user = user;
    }

    public Comment(CommentRequestDto dto) {
        this.text = dto.getText();
        this.createDate = LocalDateTime.now();
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getCommentList().add(this);
    }

    // 서비스 메서드
    public void setText(String text) {
        this.text = text;
    }
}
