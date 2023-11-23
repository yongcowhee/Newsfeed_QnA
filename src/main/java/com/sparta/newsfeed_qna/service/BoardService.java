package com.sparta.newsfeed_qna.service;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import com.sparta.newsfeed_qna.dto.BoardResponseDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.User;
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
    public void createBoard(BoardRequestDto boardRequestDto, User user) {
        Board board = new Board(boardRequestDto, user);
        boardRepository.save(board);
    }

    // 게시글 전체 조회 API
    public List<BoardResponseDto> getAllBoard() {
        // 작성일 기준 내림차순 Sort.by 사용
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(BoardResponseDto::new).toList();
    }

    // 게시글 선택 조회 API
    public BoardResponseDto selectBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()
                -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }

    // 게시글 수정 API
    @Transactional
    public BoardResponseDto modifyBoard(Long boardId, BoardRequestDto boardRequestDto, User user) {
        // 해당 게시글이 db에 존재하는지 확인, 영속성 컨텍스트 1차 캐시 저장
        Board board = findBoard(boardId); // boardRepository에서 Board 조회
        if(board.getUser().getUserId().equals(user.getUserId())){ // 인가받은 user와 repository에 저장된 board의 user가 같은지 확인
            board.update(boardRequestDto);
            return new BoardResponseDto(board);
        } else{
            throw new IllegalArgumentException("해당 게시글의 작성자만 글을 수정할 수 있습니다.");
        }
    }

    // 게시글 삭제 API
    public void deleteBoard(Long boardId, User user) {
        Board board = findBoard(boardId);
        if(board.getUser().getUserId() == user.getUserId()){
            boardRepository.delete(board);
        } else {

        }
    }

    // findById를 따로 뺀 메서드
    private Board findBoard(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
