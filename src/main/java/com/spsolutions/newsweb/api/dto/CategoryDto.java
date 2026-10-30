package com.spsolutions.newsweb.api.dto;

import com.spsolutions.newsweb.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A news category")
public record CategoryDto(
        @Schema(description = "Unique id", example = "1") Long id,
        @Schema(description = "Category name", example = "Sports") String name) {

    public static CategoryDto from(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
