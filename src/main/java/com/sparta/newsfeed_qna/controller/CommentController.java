package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import com.sparta.newsfeed_qna.dto.CommentResponseDto;
import com.sparta.newsfeed_qna.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성
    @PostMapping
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    // 모든 댓글 조회
    @GetMapping
    public List<CommentResponseDto> getAllComments() {
        return commentService.getAllComments();
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public CommentResponseDto editComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return commentService.editComment(commentId, requestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public void removeComment(@PathVariable Long commentId) {
        commentService.removeComment(commentId);
    }
}
