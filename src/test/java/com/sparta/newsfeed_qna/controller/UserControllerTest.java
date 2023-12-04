package com.sparta.newsfeed_qna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed_qna.dto.LoginRequestDto;
import com.sparta.newsfeed_qna.dto.UserSignupRequestDto;
import com.sparta.newsfeed_qna.entity.User;
import com.sparta.newsfeed_qna.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @DisplayName("signup success")
    @Test
    void signupUser() throws Exception {
        // given
        var request = new UserSignupRequestDto();
        request.setUserName("용소희");
        request.setEmail("test@spa.com");
        request.setPassword("1234");
        // when // then
        mockMvc.perform(
                        post("/signup")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.message").value("사용자가 성공적으로 등록되었습니다.")
                );
    }

    @DisplayName("login success")
    @Test
    void login() throws Exception {
        var request = new LoginRequestDto();
        request.setUserName("용소희");
        request.setPassword("1234");
        saveUser("용소희", "1234", "test@spa.com");

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.message").value("로그인에 성공했습니다."),
                        jsonPath("$.status").value("200")
                );
    }

    private User saveUser(String username, String password, String email) {
        var signup = new UserSignupRequestDto();
        signup.setUserName(username);
        signup.setPassword(passwordEncoder.encode(password));
        signup.setEmail(email);
        return userRepository.saveAndFlush(new User(signup));
    }
}