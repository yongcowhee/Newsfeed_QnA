package com.sparta.newsfeed_qna.repository;

import com.sparta.newsfeed_qna.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository <Comment, Long> {
}
