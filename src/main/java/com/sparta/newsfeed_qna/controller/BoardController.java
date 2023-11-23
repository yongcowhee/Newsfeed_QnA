package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import com.sparta.newsfeed_qna.dto.BoardResponseDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.security.UserDetailsImpl;
import com.sparta.newsfeed_qna.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 작성 API
    @PostMapping
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        boardService.createBoard(boardRequestDto, userDetails.getUser());
        return null;
    }

    // 전체 게시글 조회 API
    @GetMapping
    public List<BoardResponseDto> viewAllBoard(){
        return boardService.getAllBoard();
    }

    // 선택한 게시글 조회 API
    @GetMapping("/{boardId}")
    public Board viewBoard(@PathVariable Long boardId){
        return boardService.selectBoard(boardId);
    }

    // 게시글 수정 API
    @PatchMapping("/{boardId}")
    public BoardResponseDto modifyBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.modifyBoard(boardId, boardRequestDto, userDetails.getUser());
    }

    // 게시글 삭제 API
    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        boardService.deleteBoard(boardId, userDetails.getUser());
    }
}
