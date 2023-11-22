package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import com.sparta.newsfeed_qna.dto.BoardResponseDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 작성 API
    @PostMapping
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto){
        boardService.createBoard(boardRequestDto);
        return null;
    }

    // 전체 게시글 조회 API
    @GetMapping
    public List<BoardResponseDto> getBoard(){
        return boardService.getBoard();
    }

    // 선택한 게시글 조회 API
    @GetMapping("/{boardId}")
    public Board selectBoard(@PathVariable Long boardId){
        return boardService.selectBoard(boardId);
    }

    // 게시글 수정 API
    @PutMapping("/{boardId}")
    public Long modifyBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto){
        return boardService.modifyBoard(boardId, boardRequestDto);
    }

    // 게시글 삭제 API
    @DeleteMapping("/{boardId}")
    public Long deleteBoard(@PathVariable Long boardId){
        return boardService.deleteBoard(boardId);
    }
}

