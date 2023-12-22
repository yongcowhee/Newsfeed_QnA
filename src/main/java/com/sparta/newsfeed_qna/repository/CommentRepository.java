package com.sparta.newsfeed_qna.repository;

import com.sparta.newsfeed_qna.entity.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository <Comment, Long> {
    List<Comment> findAllByBoard_BoardId(Long boardId, Pageable pageable);
}
