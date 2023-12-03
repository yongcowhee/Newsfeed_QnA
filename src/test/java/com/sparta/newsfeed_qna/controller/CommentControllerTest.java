//package com.sparta.newsfeed_qna.controller;
//
//import com.sparta.newsfeed_qna.config.WebSecurityConfig;
//import com.sparta.newsfeed_qna.service.CommentService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//
//@WebMvcTest(
//        controllers =  {CommentController.class},
//        excludeFilters = {
//                @ComponentScan.Filter(
//                        type = FilterType.ASSIGNABLE_TYPE,
//                        classes = WebSecurityConfig.class
//                )
//        }
//)
//class CommentControllerTest {
//
//    private MockMvc mvc;
//
//    @Autowired
//    private WebApplicationContext context;
//    @MockBean
//    CommentService commentService;
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
//    }
//
//    @Test
//    void createComment() {
//    }
//
//    @Test
//    void getAllComments() {
//    }
//
//    @Test
//    void editComment() {
//    }
//
//    @Test
//    void removeComment() {
//    }
//}