package com.sparta.newsfeed_qna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed_qna.config.WebSecurityConfig;
import com.sparta.newsfeed_qna.dto.CommentCreateRequestDto;
import com.sparta.newsfeed_qna.dto.CommentResponseDto;
import com.sparta.newsfeed_qna.dto.UserSignupRequestDto;
import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.Comment;
import com.sparta.newsfeed_qna.entity.User;
import com.sparta.newsfeed_qna.security.UserDetailsImpl;
import com.sparta.newsfeed_qna.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers =  {CommentController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    CommentService commentService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createComment() throws Exception{
        // given
        var request = new CommentCreateRequestDto("text");
        var signupRequestDto = new UserSignupRequestDto();
        signupRequestDto.setPassword("1234");
        signupRequestDto.setUserName("test");
        signupRequestDto.setEmail("test@spa.com");
        var user = new User(signupRequestDto);
        var userDetails = new UserDetailsImpl(user);
        var auth =  new UsernamePasswordAuthenticationToken(userDetails, null, null);

        var board = new Board();
        var comment = new Comment(request, user, board);

        setField(board, "boardId", 10L);
        setField(comment, "commentId", 2L);
        setField(comment, "createdAt", LocalDateTime.of(2000, 10, 10, 12, 1, 1));

        var response = new CommentResponseDto(comment);
        given(commentService.createComment(eq(1L), any(), any())).willReturn(response);
        // when // then
        mvc.perform(post("/api/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authentication(auth))
                        .with(csrf()) )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.boardId").value(board.getBoardId()),
                        jsonPath("$.commentId").value(comment.getCommentId()),
                        jsonPath("$.commentAuthor").value(comment.getUser().getUserName()),
                        jsonPath("$.text").value(comment.getText()),
                        jsonPath("$.createDate").value(comment.getCreatedAt().toString())
                );
    }

    @Test
    void getAllComments() {
    }

    @Test
    void editComment() {
    }

    @Test
    void removeComment() {
    }
}