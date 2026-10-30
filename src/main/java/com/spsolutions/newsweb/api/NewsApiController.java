package com.spsolutions.newsweb.api;

import com.spsolutions.newsweb.api.dto.CommentDto;
import com.spsolutions.newsweb.api.dto.CommentRequest;
import com.spsolutions.newsweb.api.dto.NewsDetailDto;
import com.spsolutions.newsweb.entity.Comment;
import com.spsolutions.newsweb.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "News", description = "Read full news items and post comments")
@RestController
@RequestMapping("/api/news")
public class NewsApiController {

    private static final Logger log = LoggerFactory.getLogger(NewsApiController.class);

    private final NewsService newsService;

    public NewsApiController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Operation(summary = "Get a full news item with its comments")
    @GetMapping("/{newsId}")
    public NewsDetailDto getNews(@PathVariable Long newsId) {
        log.info("API: fetch news id={}", newsId);
        return NewsDetailDto.from(newsService.getWithComments(newsId));
    }

    @Operation(summary = "Add a comment to a news item")
    @PostMapping("/{newsId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long newsId,
                                                 @Valid @RequestBody CommentRequest request) {
        log.info("API: add comment to news id={} by '{}'", newsId, request.authorName());
        Comment saved = newsService.addComment(newsId, request.authorName(), request.content());
        log.debug("API: saved comment id={} on news id={}", saved.getId(), newsId);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommentDto.from(saved));
    }
}
