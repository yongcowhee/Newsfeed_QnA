package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.BoardHeartResponseDto;
import com.sparta.newsfeed_qna.security.UserDetailsImpl;
import com.sparta.newsfeed_qna.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/{boardId}")
    public BoardHeartResponseDto boardLike(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return heartService.likeBoards(boardId, userDetails.getUser());
    }
}
