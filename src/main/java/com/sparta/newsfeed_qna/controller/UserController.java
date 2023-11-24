package com.sparta.newsfeed_qna.controller;

import com.sparta.newsfeed_qna.dto.MessageDto;
import com.sparta.newsfeed_qna.dto.UserProfileModifyRequestDto;
import com.sparta.newsfeed_qna.dto.UserProfileModifyResponseDto;
import com.sparta.newsfeed_qna.dto.UserSignupRequestDto;
import com.sparta.newsfeed_qna.security.UserDetailsImpl;
import com.sparta.newsfeed_qna.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/api/users/profile")
    public ResponseEntity<?> editUserProfile(@RequestBody UserProfileModifyRequestDto userProfileModifyRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.editUserProfile(userProfileModifyRequestDto, userDetails.getUser());
        return ResponseEntity.ok(new MessageDto("사용자 프로필을 수정하였습니다."));
    }
}