package com.example.AFL.repository;

import com.example.AFL.dto.ArticleDto;
import com.example.AFL.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> { // 기본적으로 jpa 사용

}
