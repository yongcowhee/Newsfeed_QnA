package com.sparta.newsfeed_qna.service;

import com.sparta.newsfeed_qna.dto.BoardHeartResponseDto;
import com.sparta.newsfeed_qna.dto.CommentHeartResponseDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.Comment;
import com.sparta.newsfeed_qna.entity.Heart;
import com.sparta.newsfeed_qna.entity.User;
import com.sparta.newsfeed_qna.repository.BoardRepository;
import com.sparta.newsfeed_qna.repository.CommentRepository;
import com.sparta.newsfeed_qna.repository.HeartRepository;
import com.sparta.newsfeed_qna.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 게시글 좋아요
    public BoardHeartResponseDto likeBoards(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Heart findHeart = heartRepository.findHeartByBoard(board).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글에 달린 좋아요가 없습니다."));

        if (!findHeart.getUser().getUserId().equals(user.getUserId())) {
            Heart heart = new Heart(board, user);
            heartRepository.save(heart);
            board.plusLike();

            return new BoardHeartResponseDto(board.getBoardTitle(),
                    user.getUserName(), "좋아요 On!");
        } else {
            heartRepository.delete(findHeart);
            board.minusLike();
            return new BoardHeartResponseDto("좋아요 Off!");
        }
    }
}
