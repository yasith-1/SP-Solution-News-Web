package com.spsolutions.newsweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Payload for posting a new comment")
public record CommentRequest(
        @Schema(description = "Commenter name", example = "Nimal")
        @NotBlank(message = "Please enter your name")
        @Size(max = 100, message = "Name is too long")
        String authorName,

        @Schema(description = "Comment text", example = "Great article!")
        @NotBlank(message = "Comment cannot be empty")
        @Size(max = 2000, message = "Comment is too long (max 2000 characters)")
        String content) {
}
