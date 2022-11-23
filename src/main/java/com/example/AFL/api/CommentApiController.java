package com.example.AFL.api;

import com.example.AFL.dto.ArticleDto;
import com.example.AFL.dto.CommentDto;
import com.example.AFL.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentApiController {

    // 비즈니스 로직은 서비스에서 처청
    @Autowired
    private CommentService commentService;

    // 댓글 조회 api
    @GetMapping("/board/free/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        List<CommentDto> dtos = commentService.comments(articleId); // service에게 특정 게시글의 댓글 목록을 찾아달라 요청

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성 api
    @PostMapping("/board/free/{articleId}/create-comment")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {

        CommentDto created = commentService.create(articleId, dto); // service에게 특정 게시글에서 전달받은 dto 내용으로 댓글을 작성해달라고 요청

        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    // 댓글 수정 api
    @PatchMapping("/board/free/edit-comment/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {

        CommentDto updated = commentService.update(id, dto); // service에게 해당 id의 댓글을 전달받은 dto 내용으로 수정해달라고 요청

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 댓글 삭제 api
    @DeleteMapping ("/board/free/delete-comment/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {

        CommentDto deleted = commentService.delete(id); // service에게 해당 id의 댓글을 삭제해달라고 요청

        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

}
