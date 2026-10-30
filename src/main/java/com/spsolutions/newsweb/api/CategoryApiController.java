package com.spsolutions.newsweb.api;

import com.spsolutions.newsweb.api.dto.CategoryDto;
import com.spsolutions.newsweb.api.dto.NewsSummaryDto;
import com.spsolutions.newsweb.service.CategoryService;
import com.spsolutions.newsweb.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Categories", description = "Browse news categories and their headlines")
@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    private static final Logger log = LoggerFactory.getLogger(CategoryApiController.class);

    private final CategoryService categoryService;
    private final NewsService newsService;

    public CategoryApiController(CategoryService categoryService, NewsService newsService) {
        this.categoryService = categoryService;
        this.newsService = newsService;
    }

    @Operation(summary = "List all categories")
    @GetMapping
    public List<CategoryDto> listCategories() {
        log.info("API: list all categories");
        List<CategoryDto> result = categoryService.findAll().stream()
                .map(CategoryDto::from)
                .toList();
        log.debug("API: returning {} categories", result.size());
        return result;
    }

    @Operation(summary = "List the headlines in a category")
    @GetMapping("/{categoryId}/news")
    public List<NewsSummaryDto> headlinesInCategory(@PathVariable Long categoryId) {
        log.info("API: list headlines for category id={}", categoryId);
        // Validates the category exists (throws ResourceNotFoundException -> 404) before listing.
        categoryService.getById(categoryId);
        List<NewsSummaryDto> result = newsService.findHeadlinesByCategory(categoryId).stream()
                .map(NewsSummaryDto::from)
                .toList();
        log.debug("API: category id={} has {} headlines", categoryId, result.size());
        return result;
    }
}
