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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{boardId}")
    public Object createComment(@PathVariable Long boardId, @RequestBody CommentCreateRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            return commentService.createComment(boardId, requestDto, userDetails.getUser());
        } catch (RuntimeException e) {
            throw new NullPointerException("유효하지 않은 uri입니다.");
        }
    }

    //  board별 모든 댓글 조회
    @GetMapping("/{boardId}")
    public List<CommentResponseDto> getAllComments(@PathVariable Long boardId) {
        return commentService.getAllComments(boardId);
    }

    // 댓글 수정
    @PatchMapping("/{boardId}")
    public void editComment(@PathVariable Long boardId, @RequestBody CommentRequestDto requestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        commentService.editComment(boardId, requestDto, userDetails.getUser());
        String apiUri = "/api/comments/" + boardId;
        try {
            response.sendRedirect(apiUri);
        } catch (IOException e) {
            throw new NullPointerException("유효하지 않은 uri입니다.");
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{boardId}/{commentId}")
    public void removeComment(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        commentService.removeComment(boardId, commentId, userDetails.getUser());
        String apiUrl = "/api/comments/" + boardId;
        try {
            response.sendRedirect(apiUrl);
        } catch (IOException e) {
            throw new NullPointerException("유효하지 않은 uri입니다.");
        }
    }
}