package com.sparta.newsfeed_qna.repository;

import com.sparta.newsfeed_qna.entity.Board;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllBy(Pageable pageable);
}
