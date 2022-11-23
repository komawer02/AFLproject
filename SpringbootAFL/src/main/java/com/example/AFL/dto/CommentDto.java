package com.example.AFL.dto;

import com.example.AFL.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String nickname;

    private String content;

    @JsonProperty("article_id")
    private Long articleId;

    private String writeTime;

    public static CommentDto createCommentDto(Comment comment) {

        String formattedTime = "null";

        if (comment.getWriteTime() != null) {
            formattedTime = comment.getWriteTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM));
        }

        return new CommentDto(
                comment.getId(),
                comment.getNickname(),
                comment.getContent(),
                comment.getArticle().getId(),
                formattedTime
        );
    }
}
