package com.sparta.newsfeed_qna.entity;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String boardTitle;

    @Column(nullable = false)
    private String boardContent;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long heartCount;

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

    public void plusLike(){
        this.heartCount += 1;
    }

    public void minusLike(){
        this.heartCount -= 1;
    }

}