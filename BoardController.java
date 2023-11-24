package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import com.sparta.newsfeed_qna.dto.BoardResponseDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.security.UserDetailsImpl;
import com.sparta.newsfeed_qna.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 작성 API
    @PostMapping
    public RedirectView createBoard(@RequestBody BoardRequestDto boardRequestDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        boardService.createBoard(boardRequestDto, userDetails.getUser());
        response.setHeader("Location", "api/board");
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        return new RedirectView("/api/board");
    }

    // 전체 게시글 조회 API
    @GetMapping
    public List<BoardResponseDto> viewAllBoard(){
        return boardService.getAllBoard();
    }

    // 선택한 게시글 조회 API
    @GetMapping("/{boardId}")
    public BoardResponseDto viewBoard(@PathVariable Long boardId){
        return boardService.selectBoard(boardId);
    }

    // 게시글 수정 API
    @PatchMapping("/{boardId}")
    public RedirectView modifyBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        boardService.modifyBoard(boardId, boardRequestDto, userDetails.getUser());
        response.setHeader("Location","/api/board/" + boardId);
        return new RedirectView("/api/board/" + boardId);
    }

    // 게시글 삭제 API
    @DeleteMapping("/{boardId}")
    public RedirectView deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        boardService.deleteBoard(boardId, userDetails.getUser());
        response.setHeader("Location", "/api/board");
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        return new RedirectView("/api/board");
    }
}
