package com.sparta.newsfeed_qna.service;

import com.sparta.newsfeed_qna.dto.CommentCreateRequestDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.Comment;
import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import com.sparta.newsfeed_qna.dto.CommentResponseDto;
import com.sparta.newsfeed_qna.entity.User;
import com.sparta.newsfeed_qna.repository.BoardRepository;
import com.sparta.newsfeed_qna.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long boardId, CommentCreateRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        Comment comment = new Comment(requestDto, user, board);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 목록 조회
    public List<CommentResponseDto> getAllComments(Long boardId) {
        List<Comment> findCommentList = commentRepository.findAll();
        List<Comment> boardCommentList = new ArrayList<>();
        for (Comment c : findCommentList) {
            if (c.getBoard().getBoardId().equals(boardId)) {
                boardCommentList.add(c);
            }
        }

        return boardCommentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        // return commentRepository.findAll()
        //        .stream()
        //        .map(CommentResponseDto::new)
        //        .collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto editComment(Long boardId, CommentRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new NullPointerException("존재하지 않는 게시물 입니다."));

        Comment findComment = new Comment();
        for (Comment c : board.getCommentList()) {
            if (c.getCommentId().equals(requestDto.getCommentId())) {
                findComment = c;
                break;
            } else {
                throw new NullPointerException("해당 댓글이 존재하지 않습니다.");
            }
        }
        // comment의 userId와 userDetails의 userId가 같으면 update
        if (findComment.getUser().getUserId().equals(user.getUserId())) {
            findComment.update(requestDto);
            commentRepository.save(findComment); // 명시적 flush
        } else {
            throw new IllegalArgumentException("댓글 작성자만 댓글을 수정할 수 있습니다.");
        }
        return new CommentResponseDto(findComment);
    }

    // 댓글 삭제
    @Transactional
    public void removeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글이 존재하지 않습니다."));
        if (comment.getUser().getUserId().equals(user.getUserId())) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("댓글 작성자만 댓글을 삭제할 수 있습니다.");
        }
    }


}