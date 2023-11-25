package com.sparta.newsfeed_qna.entity;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @Column
    private String boardTitle;
    @Column
    private String boardContent;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "board", cascade=CascadeType.REMOVE)
    private List<Comment> commentList;

    // 게시글 작성
    public Board(BoardRequestDto boardRequestDto, User user) {
        this.boardTitle = boardRequestDto.getBoardTitle();
        this.boardContent = boardRequestDto.getBoardContent();
        this.user = user;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.boardTitle = boardRequestDto.getBoardTitle();
        this.boardContent = boardRequestDto.getBoardContent();
    }
}