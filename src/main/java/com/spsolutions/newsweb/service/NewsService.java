package com.spsolutions.newsweb.service;

import com.spsolutions.newsweb.entity.Comment;
import com.spsolutions.newsweb.entity.News;

import java.util.List;

/**
 * News and comment operations.
 *
 * <p>Dependency Inversion: the controller programs to this interface; the persistence
 * concern lives entirely in the implementation.</p>
 */
public interface NewsService {

    List<News> findHeadlinesByCategory(Long categoryId);

    News getWithComments(Long newsId);

    Comment addComment(Long newsId, String authorName, String content);
}
