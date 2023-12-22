package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import com.sparta.newsfeed_qna.dto.BoardResponseDto;
import com.sparta.newsfeed_qna.dto.PageDto;
import com.sparta.newsfeed_qna.security.UserDetailsImpl;
import com.sparta.newsfeed_qna.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 작성 API
    @PostMapping
    public void createBoard(@RequestBody BoardRequestDto boardRequestDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        boardService.createBoard(boardRequestDto, userDetails.getUser());
        String apiUrl = "/api/boards";
        try {
            response.sendRedirect(apiUrl);
        } catch (IOException e) {
            throw new NullPointerException("유효하지 않은 uri");
        }
    }

    // 전체 게시글 조회 API
    @GetMapping
    public List<BoardResponseDto> viewAllBoard(PageDto pageDto){
        return boardService.getAllBoard(pageDto.toPageable());
    }

    // 선택한 게시글 조회 API
    @GetMapping("/{boardId}")
    public BoardResponseDto viewBoard(@PathVariable Long boardId){
        return boardService.selectBoard(boardId);
    }

    // 게시글 수정 API
    @PatchMapping("/{boardId}")
    public void modifyBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        boardService.modifyBoard(boardId, boardRequestDto, userDetails.getUser());
        String apiUrl = "/api/boards/" + boardId;
        try {
            response.sendRedirect(apiUrl);
        } catch (IOException e) {
            throw new NullPointerException("유효하지 않은 uri 입니다.");
        }
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        boardService.deleteBoard(boardId, userDetails.getUser());
        String apiUrl = "/api/boards";
        try {
            response.sendRedirect(apiUrl);
        } catch (Exception e) {
            throw new NullPointerException("유효하지 않은 uri 입니다.");
        }
    }
}