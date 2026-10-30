package com.spsolutions.newsweb.api.dto;

import com.spsolutions.newsweb.entity.Category;
import com.spsolutions.newsweb.entity.News;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "A full news item with its categories and comments")
public record NewsDetailDto(
        @Schema(example = "1") Long id,
        String headline,
        @Schema(description = "Full article body") String content,
        LocalDateTime publishedAt,
        List<String> categories,
        List<CommentDto> comments) {

    public static NewsDetailDto from(News news) {
        List<String> categoryNames = news.getCategories().stream()
                .map(Category::getName)
                .sorted()
                .toList();
        List<CommentDto> commentDtos = news.getComments().stream()
                .map(CommentDto::from)
                .toList();
        return new NewsDetailDto(news.getId(), news.getHeadline(), news.getContent(),
                news.getPublishedAt(), categoryNames, commentDtos);
    }
}
