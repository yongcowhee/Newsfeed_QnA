package com.sparta.newsfeed_qna.repository;

import com.sparta.newsfeed_qna.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
