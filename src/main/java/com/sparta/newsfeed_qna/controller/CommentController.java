package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.CommentCreateRequestDto;
import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import com.sparta.newsfeed_qna.dto.CommentResponseDto;
import com.sparta.newsfeed_qna.security.UserDetailsImpl;
import com.sparta.newsfeed_qna.service.CommentService;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{boardId}")
    public RedirectView createComment(@PathVariable Long boardId, @RequestBody CommentCreateRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        commentService.createComment(boardId, requestDto, userDetails.getUser());
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
    @PatchMapping("/{boardId}")
    public RedirectView editComment(@PathVariable Long boardId, @RequestBody CommentRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        commentService.editComment(boardId, requestDto, userDetails.getUser());
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "/comments/" + commentId);
        return new RedirectView("/comments/" + commentId); // 수정된 댓글이 있는 페이지로 리다이렉트

    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public void removeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.removeComment(commentId, userDetails.getUser());
    }
}