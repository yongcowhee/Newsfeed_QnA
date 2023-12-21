package com.sparta.newsfeed_qna.service;

import com.sparta.newsfeed_qna.dto.UserProfileModifyRequestDto;
import com.sparta.newsfeed_qna.dto.UserProfileModifyResponseDto;
import com.sparta.newsfeed_qna.dto.UserSignupRequestDto;
import com.sparta.newsfeed_qna.entity.User;
import com.sparta.newsfeed_qna.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    * controller -> service
    * controller <- service
    *
    * */


    public void signupNewUserAccount(UserSignupRequestDto userSignupRequestDTO) throws IllegalArgumentException {
        // 패스워드 인코딩
        String password = passwordEncoder.encode(userSignupRequestDTO.getPassword());
        userSignupRequestDTO.setPassword(password);
        User user = new User(userSignupRequestDTO);

        // 이메일 중복 확인!
        if (userRepository.findByEmail(userSignupRequestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        } else if (userRepository.findByUserNickname(userSignupRequestDTO.getUserNickname()).isPresent()){
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        } else if(userSignupRequestDTO.getPassword().contains(userSignupRequestDTO.getUserName())){
            throw new IllegalArgumentException("비밀번호에는 닉네임이 포함될 수 없습니다.");
        }
        userRepository.save(user);
    }

    public UserProfileModifyResponseDto editUserProfile(UserProfileModifyRequestDto userProfileModifyRequestDto, User user) {
        User findUser = userRepository.findById(user.getUserId()).orElseThrow(()->
                new NullPointerException("존재하지 않는 사용자 입니다."));
        boolean passwordValidation = passwordEncoder.matches(userProfileModifyRequestDto.getPassword(), findUser.getPassword());
        if(passwordValidation == true){
            findUser.profileUpdate(userProfileModifyRequestDto);
            userRepository.save(findUser);
        } else {
            throw new IllegalArgumentException("사용자 본인만 프로필 정보를 수정할 수 있습니다. 비밀번호를 다시 확인해주세요.");
        }

        return new UserProfileModifyResponseDto(findUser);
    }
}
