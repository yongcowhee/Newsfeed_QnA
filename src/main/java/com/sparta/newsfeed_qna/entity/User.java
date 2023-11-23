package com.sparta.newsfeed_qna.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    public User(UserSignupRequestDto userSignupRequestDTO){
        this.userName = userSignupRequestDTO.getUserName();
        this.password = userSignupRequestDTO.getPassword();
        this.email = userSignupRequestDTO.getEmail();
    }

//    public User(String username, String password, String email) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//    }
}

