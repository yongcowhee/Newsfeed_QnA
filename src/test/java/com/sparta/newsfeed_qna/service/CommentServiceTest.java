package com.sparta.newsfeed_qna.service;

import com.sparta.newsfeed_qna.dto.BoardRequestDto;
import com.sparta.newsfeed_qna.dto.CommentCreateRequestDto;
import com.sparta.newsfeed_qna.dto.CommentRequestDto;
import com.sparta.newsfeed_qna.dto.CommentResponseDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.Comment;
import com.sparta.newsfeed_qna.entity.User;
import com.sparta.newsfeed_qna.repository.BoardRepository;
import com.sparta.newsfeed_qna.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    CommentRepository commentRepository;
    @Mock
    BoardRepository boardRepository;
    @InjectMocks
    CommentService commentService;

    @Nested
    @DisplayName("댓글 생성 테스트")
    class createComment {
        @Test
        @DisplayName("성공")
        void createCommentSuccess() {
            // given
            CommentCreateRequestDto commentCreateRequestDto = new CommentCreateRequestDto("댓글 생성 테스트");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setBoardTitle("댓글 생성");
            boardRequestDto.setBoardContent("댓글 생성 내용");

            Board board = new Board(boardRequestDto, user);

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            CommentResponseDto responseDto = commentService.createComment(board.getBoardId(), commentCreateRequestDto, user);

            // then
            verify(commentRepository, times(1)).save(any());
            assertThat(responseDto.getText()).isEqualTo("댓글 생성 테스트");
        }

        @Test
        @DisplayName("게시글 조회 실패")
        void createCommentFail_NotFoundBoard() {
            // given
            CommentCreateRequestDto commentCreateRequestDto = new CommentCreateRequestDto("댓글 생성 테스트");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setBoardTitle("게시글 생성");
            boardRequestDto.setBoardContent("게시글 생성 내용");

            Board board = new Board(boardRequestDto, user);

            when(boardRepository.findById(any())).thenThrow(new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));

            // when
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> commentService.createComment
                    (board.getBoardId(), commentCreateRequestDto, user));

            // then
            assertEquals("선택한 게시글은 존재하지 않습니다.", e.getMessage());
        }
    }


/*
    @Test
    @DisplayName("댓글 목록 조회")
    void getAllComments() {
        // given
        CommentCreateRequestDto commentCreateRequestDto = new CommentCreateRequestDto("댓글 생성 테스트");

        User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                "아리곤듀 == 언니 딸");

        Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, null);

        Comment comment = new Comment(commentCreateRequestDto, user, board);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);

        when(commentRepository.findAll()).thenReturn(commentList);

        // when
        List<CommentResponseDto> commentResponseDtoList = commentService.getAllComments(board.getBoardId());

        // then
        assertThat(commentResponseDtoList.get(0).getText()).isEqualTo("댓글 생성 테스트");
    }
*/

    @Nested
    @DisplayName("댓글 수정")
    class editComment {
        @Test
        @DisplayName("성공")
        void editComment() {
            // given
            CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "댓글 수정해주세요 이걸로");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, new ArrayList<>());
            Comment comment = new Comment(1L, "댓글", user, board);
            board.getCommentList().add(comment);

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            CommentResponseDto commentResponseDto = commentService.editComment(1L, commentRequestDto, user);

            // then
            assertThat("댓글 수정해주세요 이걸로").isEqualTo(commentResponseDto.getText());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 게시글")
        void editCommentFail_NotFoundBoard() {
            // given
            CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "댓글 수정해주세요 이걸로");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            when(boardRepository.findById(any())).thenThrow(new IllegalArgumentException("존재하지 않는 게시물 입니다."));

            // when
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> commentService.editComment(1L, commentRequestDto, user));

            // then
            assertEquals("존재하지 않는 게시물 입니다.", e.getMessage());
        }

        @Test
        @DisplayName("실패 - 작성자 불일치")
        void editCommentFail_NotEqualUser() {
            // given
            CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "댓글 수정해주세요 이걸로");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");
            User dUser = new User(2L, "용루이", "1234", "louis@gmail.com",
                    "용루이 1세", "용루이가 세상을 지배한다.");

            Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, new ArrayList<>());
            Comment comment = new Comment(1L, "댓글", user, board);

            board.getCommentList().add(comment);

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            AccessDeniedException e = assertThrows(AccessDeniedException.class, () ->
                    commentService.editComment(2L, commentRequestDto, dUser));

            // then
            assertEquals("댓글 작성자만 댓글을 수정할 수 있습니다.", e.getMessage());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 댓글")
        void editCommentFail_NotFoundComment() {
            // given
            CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "댓글 수정해주세요 이걸로");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");
            Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, new ArrayList<>());
            Comment comment = new Comment(2L, "댓글", user, board);

            board.getCommentList().add(comment);

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                    commentService.editComment(2L, commentRequestDto, user));

            // then
            assertEquals("해당 댓글이 존재하지 않습니다.", e.getMessage());
        }
    }


    @Nested
    @DisplayName("댓글 삭제")
    class removeComment {
        @Test
        @DisplayName("성공")
        void removeCommentSuccess() {
            // given
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            Board board = new Board(2L, "댓글 삭제", "댓글 삭제 테스트 중입니다.", user, new ArrayList<>());
            Comment comment = new Comment(1L, "댓글", user, board);

            board.getCommentList().add(comment);

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));
            when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

            // when
            commentService.removeComment(board.getBoardId(), comment.getCommentId(), user);

            // then
            verify(commentRepository, times(1)).delete(any());
        }

        @Test
        @DisplayName("실패 - 게시글 조회 실패")
        void removeCommentFail_NotFoundBoard() {
            // given
            CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "댓글 수정해주세요 이걸로");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            when(boardRepository.findById(any())).thenThrow(new IllegalArgumentException("존재하지 않는 게시물 입니다."));

            // when
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> commentService.editComment(1L, commentRequestDto, user));

            // then
            assertEquals("존재하지 않는 게시물 입니다.", e.getMessage());
        }

        @Test
        @DisplayName("실패 - 댓글 조회 실패")
        void removeCommentFail_NotFoundComment() {
            // given
            CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "댓글 수정해주세요 이걸로");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");
            Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, new ArrayList<>());
            Comment comment = new Comment(2L, "댓글", user, board);

            board.getCommentList().add(comment);

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));

            // when
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                    commentService.editComment(2L, commentRequestDto, user));

            // then
            assertEquals("해당 댓글이 존재하지 않습니다.", e.getMessage());
        }

        @Test
        @DisplayName("실패 - 작성자 불일치")
        void removeCommentFail_NotEqualUser() {
            // given
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");
            User dUser = new User(2L, "용루이", "1234", "louis@gmail.com",
                    "용루이 1세", "용루이가 세상을 지배한다.");

            Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, new ArrayList<>());
            Comment comment = new Comment(1L, "댓글", user, board);

            board.getCommentList().add(comment);

            when(boardRepository.findById(any())).thenReturn(Optional.of(board));
            when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

            // when
            AccessDeniedException e = assertThrows(AccessDeniedException.class, () ->
                    commentService.removeComment(board.getBoardId(), comment.getCommentId(), dUser));

            // then
            assertEquals("댓글 작성자만 댓글을 삭제할 수 있습니다.", e.getMessage());
        }

        @Test
        @DisplayName("실패 - Board에 없는 댓글")
        void removeCommentFail_NotExistComment() {
            // given
            CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "댓글 수정해주세요 이걸로");
            User user = new User(1L, "용소희", "1234", "ari@gmail.com", "아리곤듀",
                    "아리곤듀 == 언니 딸");

            Board dBoard = new Board(1L, "댓글 없는 게시글", "NotExistComment", user, new ArrayList<>());
            Board board = new Board(2L, "게시글 삭제", "게시글 삭제 테스트 중입니다.", user, new ArrayList<>());

            Comment comment = new Comment(2L, "댓글", user, board);
            board.getCommentList().add(comment);

            when(boardRepository.findById(any())).thenReturn(Optional.of(dBoard));
            when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

            // when
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                    commentService.removeComment(1L, 2L, user));

            // then
            assertEquals("선택한 게시글에 해당 댓글이 존재하지 않습니다.", e.getMessage());
        }
    }
}