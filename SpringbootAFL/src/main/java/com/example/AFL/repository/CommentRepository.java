package com.example.AFL.repository;

import com.example.AFL.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> { // 기본적으로 jpa 사용

    List<Comment> findByArticleId(Long articleId); // xml파일에 query설정
}
