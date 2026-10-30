package com.spsolutions.newsweb.service.impl;

import com.spsolutions.newsweb.entity.Comment;
import com.spsolutions.newsweb.entity.News;
import com.spsolutions.newsweb.exception.ResourceNotFoundException;
import com.spsolutions.newsweb.repository.CommentRepository;
import com.spsolutions.newsweb.repository.NewsRepository;
import com.spsolutions.newsweb.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * JPA-backed implementation of {@link NewsService}.
 *
 * <p>SRP: only news/comment coordination lives here. It depends on repository abstractions,
 * not concrete classes (Dependency Inversion), so the persistence technology can change
 * without touching the controller.</p>
 */
@Service
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    public NewsServiceImpl(NewsRepository newsRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<News> findHeadlinesByCategory(Long categoryId) {
        return newsRepository.findByCategoryId(categoryId);
    }

    @Override
    public News getWithComments(Long newsId) {
        return newsRepository.findByIdWithComments(newsId)
                .orElseThrow(() -> ResourceNotFoundException.of("News", newsId));
    }

    @Override
    @Transactional
    public Comment addComment(Long newsId, String authorName, String content) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> ResourceNotFoundException.of("News", newsId));
        Comment comment = new Comment(authorName.trim(), content.trim());
        news.addComment(comment);
        Comment saved = commentRepository.save(comment);
        log.info("Comment id={} added to news id={}", saved.getId(), newsId);
        return saved;
    }
}
