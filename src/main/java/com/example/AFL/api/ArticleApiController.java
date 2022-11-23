package com.example.AFL.api;

import com.example.AFL.dto.ArticleDto;
import com.example.AFL.entity.Article;
import com.example.AFL.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class ArticleApiController {

    // 비즈니스 로직은 서비스에서 처달
    @Autowired
    private ArticleService articleService;

    // 게시글 작성 api
    @PostMapping("/write")
    public ResponseEntity<ArticleDto> write(@RequestBody ArticleDto dto) {

        ArticleDto created = articleService.create(dto); // service에게 게시글 작성을 요청하고 작성된 게시글 dto를 반환받는다

        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    // 게시글 삭제 api
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ArticleDto> delete(@PathVariable Long id) {

        ArticleDto deleted = articleService.delete(id); // service에게 특정 id의 게시글 삭제를 요청

        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    // 게시글 수정 api
    @PatchMapping("/update/{id}")
    public ResponseEntity<ArticleDto> update(@PathVariable Long id, @RequestBody ArticleDto dto) {

        ArticleDto updated = articleService.update(id, dto); // service에게 특정 id의 게시글을 전달받은 dto로 수정해달라고 요청

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
