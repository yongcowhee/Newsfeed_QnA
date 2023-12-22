package com.sparta.newsfeed_qna.service;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import com.sparta.newsfeed_qna.dto.BoardResponseDto;
import com.sparta.newsfeed_qna.dto.UserSignupRequestDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.User;
import com.sparta.newsfeed_qna.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // @Mock 사용을 위한 설정
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;
    @InjectMocks
    BoardService boardService;

    @DisplayName("게시글 생성")
    @Test
    void createBoard() {
        // given
        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setBoardTitle("게시글 생성");
        boardRequestDto.setBoardContent("게시글 생성 내용");

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto();
        userSignupRequestDto.setUserName("용소희");
        userSignupRequestDto.setPassword("1234");
        userSignupRequestDto.setEmail("ari@gmail.com");

        User user = new User(userSignupRequestDto);

        // when
        boardService.createBoard(boardRequestDto, user);

        // then
        verify(boardRepository, times(1)).save(any());
    }

/*    @DisplayName("게시글 전체 조회")
    @Test
    void getAllBoard() {
        // given
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto();
        userSignupRequestDto.setUserName("용소희");
        userSignupRequestDto.setPassword("1234");
        userSignupRequestDto.setEmail("ari@gmail.com");

        User user = new User(userSignupRequestDto);

        Board board = new Board(2l, "조회 테스트", "조회용", user, null);

        List<Board> boardList = new ArrayList<>();

        // boardRepository의 findAll에 Sort~~ 라는 매개변수가 들어가서 호출되면 리턴해라
        when(boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))).thenReturn(boardList); // Mock 객체한테 뭘 할지 알려주는 용도
        boardList.add(board);

        // when
        List<BoardResponseDto> allBoardList = boardService.getAllBoard();

        // then
        assertEquals(allBoardList.size(), 1);
    }*/


    @Nested
    @DisplayName("게시글 선택 조회")
    class selectBoard {
        @Test
        @DisplayName("성공")
        void selectBoardSuccess() {
            // given
            UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto();
            userSignupRequestDto.setUserName("용소희");
            userSignupRequestDto.setPassword("1234");
            userSignupRequestDto.setEmail("ari@gmail.com");

            User user = new User(userSignupRequestDto);

            Board board = new Board(1L, "게시글 선택 조회", "게시글 선택 조회 테스트 중입니다.", user, null);

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            BoardResponseDto boardResponseDto = boardService.selectBoard(board.getBoardId());

            // then
            assertEquals(1L, boardResponseDto.getBoardId());
            assertEquals("게시글 선택 조회", boardResponseDto.getBoardTitle());
            assertEquals("용소희", boardResponseDto.getBoardAuthor());
            assertEquals("게시글 선택 조회 테스트 중입니다.", boardResponseDto.getBoardContent());
            assertNull(boardResponseDto.getCreateAt());
            assertNull(boardResponseDto.getModifiedAt());
        }

        @Test
        @DisplayName("실패")
        void selectBoardFail() {
            // given
            Long boardId = 1L;

            when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

            // when

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> boardService.selectBoard(boardId));

            // then
            assertEquals("선택한 게시글은 존재하지 않습니다.", e.getMessage());
        }
    }


    @Nested
    @DisplayName("게시글 수정")
    class modifyBoard {
        @DisplayName("성공")
        @Test
        void modifyBoardSuccess() {
            // given
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            Board board = new Board(1L, "게시글 수정", "게시글 수정 테스트 중입니다.", user, null);
            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setBoardTitle("수정 테스트");
            boardRequestDto.setBoardContent("수정 수정!");

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            boardService.modifyBoard(board.getBoardId(), boardRequestDto, user);

            // then
            assertEquals("수정 테스트", board.getBoardTitle());
            assertEquals("수정 수정!", board.getBoardContent());
            assertEquals(1L, board.getUser().getUserId());
            assertNull(board.getCreatedAt());
            assertNull(board.getModifiedAt());
        }

        @DisplayName("게시글 조회 실패")
        @Test
        void modifyBoardFailNotFoundBoard() {
            // given
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            Board board = new Board(2L, "게시글 수정", "게시글 수정 테스트 중입니다.", user, null);
            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setBoardTitle("수정 테스트");
            boardRequestDto.setBoardContent("수정 수정!");

            when(boardRepository.findById(2L)).thenThrow(new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));

            // when
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> boardService.modifyBoard(board.getBoardId(), boardRequestDto, user));

            // then
            assertEquals("선택한 게시글은 존재하지 않습니다.", e.getMessage());
        }

        @DisplayName("유저 불일치 실패")
        @Test
        void modifyBoardFailNotEqualUser() {
            // given
            User boardAuthor = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");
            User user = new User(2L, "다른 사람", "1234", "louis@gmail.com", "루이야",
                    "눈나 자고시퍼");

            Board board = new Board(1L, "게시글 수정", "게시글 수정 테스트 중입니다.", boardAuthor, null);

            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setBoardTitle("수정 테스트");
            boardRequestDto.setBoardContent("수정 수정!");

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            AccessDeniedException e = assertThrows(AccessDeniedException.class,
                    () -> boardService.modifyBoard(board.getBoardId(), boardRequestDto, user));

            // then
            assertEquals("해당 게시글의 작성자만 글을 수정할 수 있습니다.", e.getMessage());
        }
    }


    @DisplayName("게시글 삭제")
    @Nested
    class deleteBoard {
        @DisplayName("성공")
        @Test
        void deleteBoardSuccess() {
            // given
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, null);
            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setBoardTitle("수정 테스트");
            boardRequestDto.setBoardContent("수정 수정!");

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            boardService.deleteBoard(board.getBoardId(), user);

            // then
            verify(boardRepository, times(1)).delete(any());

        }

        @DisplayName("게시글 조회 실패")
        @Test
        void deleteBoardFail_NotFoundBoard() {
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, null);
            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setBoardTitle("삭제 테스트");
            boardRequestDto.setBoardContent("삭제 삭제!");

            when(boardRepository.findById(2L)).thenThrow(new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));

            // when
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> boardService.modifyBoard(board.getBoardId(), boardRequestDto, user));

            // then
            assertEquals("선택한 게시글은 존재하지 않습니다.", e.getMessage());
        }

        @DisplayName("유저 불일치 실패")
        @Test
        void deleteBoardFail_NotEqualUser() {
            // given
            User boardAuthor = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");
            User user = new User(2L, "다른 사람", "1234", "louis@gmail.com", "루이야",
                    "눈나 자고시퍼");

            Board board = new Board(1L, "게시글 수정", "게시글 수정 테스트 중입니다.", boardAuthor, null);

            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setBoardTitle("수정 테스트");
            boardRequestDto.setBoardContent("수정 수정!");

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            AccessDeniedException e = assertThrows(AccessDeniedException.class,
                    () -> boardService.deleteBoard(board.getBoardId(), user));

            // then
            assertEquals("해당 게시글의 작성자만 글을 삭제할 수 있습니다.", e.getMessage());
        }
    }
}
