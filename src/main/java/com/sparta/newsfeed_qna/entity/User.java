package com.sparta.newsfeed_qna.entity;

import com.sparta.newsfeed_qna.dto.UserProfileModifyRequestDto;
import com.sparta.newsfeed_qna.dto.UserSignupRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String userNickname;
    @Column(nullable = false)
    private String oneLiner; // 한줄 소개

    public User(UserSignupRequestDto userSignupRequestDTO){
        this.userName = userSignupRequestDTO.getUserName();
        this.password = userSignupRequestDTO.getPassword();
        this.email = userSignupRequestDTO.getEmail();
        this.userNickname = userSignupRequestDTO.getUserNickname();
    }

    public void profileUpdate(UserProfileModifyRequestDto userProfileModifyRequestDto){
        this.userNickname = userProfileModifyRequestDto.getUserNickName();
        this.oneLiner = userProfileModifyRequestDto.getUserOneLiner();
    }
}

