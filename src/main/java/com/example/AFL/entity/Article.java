package com.example.AFL.entity;

import com.example.AFL.dto.ArticleDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id를 자동 생성 어노테이션
    private Long id;

    @Column
    private String nickname;

    @Column
    private String title;

    @Column
    private String content;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public static Article createArticle(ArticleDto dto) {
        if (dto.getId() != null)
            throw new IllegalArgumentException("글 생성 실패! id가 존재함");

        return new Article(
                dto.getId(),
                dto.getNickname(),
                dto.getTitle(),
                dto.getContent(),
                dto.getComments()
        );
    }

    public void patch(ArticleDto dto) {
        if (this.id != dto.getId()) {
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        }

        if (dto.getTitle() != "") {
            this.title = dto.getTitle();
        }

        if (dto.getContent() != "") {
            this.content = dto.getContent();
        }

    }


}
