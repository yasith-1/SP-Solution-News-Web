package com.spsolutions.newsweb.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentForm {

    @NotBlank(message = "Please enter your name")
    @Size(max = 100, message = "Name is too long")
    private String authorName;

    @NotBlank(message = "Comment cannot be empty")
    @Size(max = 2000, message = "Comment is too long (max 2000 characters)")
    private String content;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
