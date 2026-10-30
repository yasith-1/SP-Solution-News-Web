package com.spsolutions.newsweb.api.dto;

import com.spsolutions.newsweb.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "A reader comment on a news item")
public record CommentDto(
        @Schema(example = "10") Long id,
        @Schema(description = "Name of the commenter", example = "Nimal") String authorName,
        @Schema(description = "Comment body", example = "Great article!") String content,
        @Schema(description = "When the comment was posted") LocalDateTime createdAt) {

    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getId(), comment.getAuthorName(),
                comment.getContent(), comment.getCreatedAt());
    }
}
