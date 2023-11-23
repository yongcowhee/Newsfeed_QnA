package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.MessageDto;
import com.sparta.newsfeed_qna.dto.UserSignupRequestDto;
import com.sparta.newsfeed_qna.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody UserSignupRequestDto userSignupRequestDTO) {
        userService.signupNewUserAccount(userSignupRequestDTO);
        return ResponseEntity.ok(new MessageDto("사용자가 성공적으로 등록되었습니다."));
    }
}