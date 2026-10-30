package com.spsolutions.newsweb.web;

import com.spsolutions.newsweb.entity.Category;
import com.spsolutions.newsweb.entity.News;
import com.spsolutions.newsweb.service.CategoryService;
import com.spsolutions.newsweb.service.NewsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NewsController {

    private static final Logger log = LoggerFactory.getLogger(NewsController.class);

    private final CategoryService categoryService;
    private final NewsService newsService;

    public NewsController(CategoryService categoryService, NewsService newsService) {
        this.categoryService = categoryService;
        this.newsService = newsService;
    }

    /** Step 1: home page showing all categories. */
    @GetMapping("/")
    public String home(Model model) {
        log.info("Web: render categories home page");
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    /** Step 2: headlines within the selected category. */
    @GetMapping("/categories/{categoryId}")
    public String headlines(@PathVariable Long categoryId, Model model) {
        log.info("Web: render headlines for category id={}", categoryId);
        Category category = categoryService.getById(categoryId);
        List<News> headlines = newsService.findHeadlinesByCategory(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("headlines", headlines);
        return "headlines";
    }

    /** Step 3: full news item with its comments and the comment form. */
    @GetMapping("/news/{newsId}")
    public String newsDetail(@PathVariable Long newsId,
                             @RequestParam(value = "fromCategory", required = false) Long fromCategory,
                             Model model) {
        log.info("Web: render news detail id={}", newsId);
        News news = newsService.getWithComments(newsId);
        model.addAttribute("news", news);
        model.addAttribute("fromCategory", fromCategory);
        if (!model.containsAttribute("commentForm")) {
            model.addAttribute("commentForm", new CommentForm());
        }
        return "news-detail";
    }

    /** Step 4: readers add a comment to a news item. */
    @PostMapping("/news/{newsId}/comments")
    public String addComment(@PathVariable Long newsId,
                             @RequestParam(value = "fromCategory", required = false) Long fromCategory,
                             @Valid @ModelAttribute("commentForm") CommentForm commentForm,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("Web: comment validation failed for news id={} ({} error(s))",
                    newsId, bindingResult.getErrorCount());
            // Re-render the detail page showing validation errors.
            News news = newsService.getWithComments(newsId);
            model.addAttribute("news", news);
            model.addAttribute("fromCategory", fromCategory);
            return "news-detail";
        }
        log.info("Web: adding comment to news id={} by '{}'", newsId, commentForm.getAuthorName());
        newsService.addComment(newsId, commentForm.getAuthorName(), commentForm.getContent());
        redirectAttributes.addFlashAttribute("commentSaved", true);
        String redirect = "redirect:/news/" + newsId;
        if (fromCategory != null) {
            redirect += "?fromCategory=" + fromCategory;
        }
        return redirect;
    }
}
