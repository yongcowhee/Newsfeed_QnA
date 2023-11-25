package com.sparta.newsfeed_qna.repository;

import com.sparta.newsfeed_qna.entity.Board;
import com.sparta.newsfeed_qna.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findHeartByBoard(Board board);

}
