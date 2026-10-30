package com.spsolutions.newsweb.api.dto;

import com.spsolutions.newsweb.entity.Category;
import com.spsolutions.newsweb.entity.News;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "A news headline summary (used in category listings)")
public record NewsSummaryDto(
        @Schema(example = "1") Long id,
        @Schema(description = "Headline text") String headline,
        @Schema(description = "Publish timestamp") LocalDateTime publishedAt,
        @Schema(description = "Names of all categories this news belongs to") List<String> categories) {

    public static NewsSummaryDto from(News news) {
        List<String> categoryNames = news.getCategories().stream()
                .map(Category::getName)
                .sorted()
                .toList();
        return new NewsSummaryDto(news.getId(), news.getHeadline(), news.getPublishedAt(), categoryNames);
    }
}
