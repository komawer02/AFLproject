package com.example.AFL.service;

import com.example.AFL.dto.ArticleDto;
import com.example.AFL.entity.Article;
import com.example.AFL.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // 게시판 전체 글 조회
    public List<ArticleDto> showAll() {
        // 글 작성시간에 따른 내림차순 정렬
        return articleRepository.findAll(Sort.by(Sort.Direction.DESC, "writeTime"))
                .stream()
                .map(article -> ArticleDto.createArticleDto(article)) // entity형태의 값들을 dto형으로 변환 후 반환
                .collect(Collectors.toList());
    }

    // 게시글 작성 로직
    @Transactional
    public ArticleDto create(ArticleDto dto) {
        // dto를 entity로
        Article article = Article.createArticle(dto);

        // repository를 이용하여 저장
        Article created = articleRepository.save(article); // 전달받은 게시글 entity를 DB에 저장

        // 반환
        return ArticleDto.createArticleDto(created);
    }

    // 특정 게시글 조회 로직
    public ArticleDto show(Long id) {

        Article article = articleRepository.findById(id) // 게시글을 조회, 없으면 오류 발생
                .orElseThrow(() -> new IllegalArgumentException("조회 실패! id에 해당하는 글이 존재하지 않음"));

        ArticleDto articleDto = ArticleDto.createArticleDto(article); // DB에서 찾은 entity형태의 게시글을 dto형으로 전환 후 반환

        return articleDto;

    }

    // 게시글 삭제 로직
    @Transactional
    public ArticleDto delete(Long id) {

        Article target = articleRepository.findById(id) // 게시글 조회, 없으면 오류 발생
                .orElseThrow(() -> new IllegalArgumentException("글삭제 실패! 해당 글의 id가 잘못되었습니다."));

        articleRepository.delete(target); // 선택한 게시글을 DB에서 삭제

        return ArticleDto.createArticleDto(target);
    }

    // 게시글 수정 로직
    @Transactional
    public ArticleDto update(Long id, ArticleDto dto) {

        Article target = articleRepository.findById(id) // 게시글 조회, 없으면 오류 발생
                .orElseThrow(() -> new IllegalArgumentException("글 불러오기 실패! 해당글의 id가 잘못되었습니다."));

        target.patch(dto); // 게시리 수정을 위한 patch 메소드를 entity에 구현

        Article updated = articleRepository.save(target); // DB에 수정된 게시글을 저장(PK인 id가 같으므로 수정처리)

        return ArticleDto.createArticleDto(updated);
    }
}
