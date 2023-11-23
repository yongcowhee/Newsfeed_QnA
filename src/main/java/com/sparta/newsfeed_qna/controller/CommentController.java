package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.CommentCreateRequestDto;
import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import com.sparta.newsfeed_qna.dto.CommentResponseDto;
import com.sparta.newsfeed_qna.security.UserDetailsImpl;
import com.sparta.newsfeed_qna.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{boardId}")
    public CommentResponseDto createComment(@PathVariable Long boardId, @RequestBody CommentCreateRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(boardId, requestDto, userDetails.getUser());
    }

    // 모든 댓글 조회
    @GetMapping
    public List<CommentResponseDto> getAllComments() {
        return commentService.getAllComments();
    }

    // 댓글 수정
    @PatchMapping("/{boardId}")
    public CommentResponseDto editComment(@PathVariable Long boardId, @RequestBody CommentRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.editComment(boardId, requestDto, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public void removeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.removeComment(commentId, userDetails.getUser());
    }
}
