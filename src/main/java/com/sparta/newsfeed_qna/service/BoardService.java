package com.sparta.newsfeed_qna.service;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import com.sparta.newsfeed_qna.dto.BoardResponseDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 작성 API
    public void createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);

        boardRepository.save(board);
    }

    // 게시글 전체 조회 API
    public List<BoardResponseDto> getBoard() {
        // 작성일 기준 내림차순 Sort.by 사용
        List<BoardResponseDto> boardResponseDtos = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(BoardResponseDto::new).toList();
        return boardResponseDtos;
    }

    // 게시글 선택 조회 API
    public Board selectBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
    // 게시글 수정 API
    @Transactional
    public Long modifyBoard(Long boardId, BoardRequestDto boardRequestDto) {
        // 해당 게시글이 db에 존재하는지 확인
        Board board = findBoard(boardId);

        board.update(boardRequestDto);
        return boardId;
    }

    // 게시글 삭제 API
    public Long deleteBoard(Long boardId) {
        Board board = findBoard(boardId);

        boardRepository.delete(board);
        return boardId;
    }

    // findById를 따로 뺀 메서드
    private Board findBoard(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}