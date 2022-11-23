package com.example.AFL.dto;

import com.example.AFL.entity.Article;
import com.example.AFL.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;


@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Long id;

    private String nickname;

    private String title;

    private String content;

    private String writeTime;

    private List<Comment> comments;

    public static ArticleDto createArticleDto(Article article) {

        String formattedTime = "null";


        if (article.getWriteTime() != null) {
            formattedTime = article.getWriteTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM));
        }


        return new ArticleDto(
                article.getId(),
                article.getNickname(),
                article.getTitle(),
                article.getContent(),
                formattedTime,
                article.getComments()
        );
    }


}
