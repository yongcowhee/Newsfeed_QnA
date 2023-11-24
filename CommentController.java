package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import com.sparta.newsfeed_qna.dto.CommentResponseDto;
import com.sparta.newsfeed_qna.service.CommentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView createComment(@RequestBody CommentRequestDto requestDto, HttpServletResponse response) {
        CommentResponseDto commentResponseDto = commentService.createComment(requestDto);
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "/comments");
        return new RedirectView("/comments"); // 댓글 목록 페이지 또는 댓글이 추가된 페이지로 리다이렉트
    }

    // 모든 댓글 조회
    @GetMapping
    public List<CommentResponseDto> getAllComments() {
        return commentService.getAllComments();
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public RedirectView editComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, HttpServletResponse response) {
        CommentResponseDto commentResponseDto = commentService.editComment(commentId, requestDto);
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "/comments/" + commentId);
        return new RedirectView("/comments/" + commentId); // 수정된 댓글이 있는 페이지로 리다이렉트
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public void removeComment(@PathVariable Long commentId) {
        commentService.removeComment(commentId);
    }
}
