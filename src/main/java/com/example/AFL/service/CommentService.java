package com.example.AFL.service;

import com.example.AFL.dto.CommentDto;
import com.example.AFL.entity.Article;
import com.example.AFL.entity.Comment;
import com.example.AFL.repository.ArticleRepository;
import com.example.AFL.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // 댓글 작성 로직
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {

        Article article = articleRepository.findById(articleId) // 해당 댓글의 부모인 게시글 조회, 없으면 오류 발생
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        Comment comment = Comment.createComment(dto, article);

        Comment created = commentRepository.save(comment); // 전달받은 댓글 entity를 DB에 저장

        return CommentDto.createCommentDto(created);
    }

    // 특정 게시글의 댓글들 조회 로직
    public List<CommentDto> comments(Long articleId) {

        return commentRepository.findByArticleId(articleId) // 특정 게시글을 조회한 후 연결된 댓글들 반환
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    // 댓글 수정 로직
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {

        Comment target = commentRepository.findById(id) // 댓글의 id 조회, 없으면 오류 발생
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 해당 댓글을 찾을 수 없습니다."));

        target.patch(dto); // 댓글 수정을 위한 patch 메소드를 entity에 구현

        Comment updated = commentRepository.save(target); // 수정한 댓글 entity를 DB에 저장

        return CommentDto.createCommentDto(updated);
    }

    // 댓글 삭제 로직
    @Transactional
    public CommentDto delete(Long id) {

        Comment target = commentRepository.findById(id) // 댓글의 id 조회, 없으면 오류 발생
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상 댓글이 없습니다."));

        commentRepository.delete(target); // 해당 댓글을 DB에서 삭제

        return CommentDto.createCommentDto(target);

    }
}

