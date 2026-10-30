package com.spsolutions.newsweb;

import com.spsolutions.newsweb.entity.Category;
import com.spsolutions.newsweb.entity.News;
import com.spsolutions.newsweb.service.CategoryService;
import com.spsolutions.newsweb.service.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // keep a session open so lazy associations can be read within each test
class NewsWebApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NewsService newsService;

    @Test
    void contextLoadsAndSeedsCategories() {
        List<Category> categories = categoryService.findAll();
        assertThat(categories).isNotEmpty();
    }

    @Test
    void newsCanBelongToMultipleCategories() {
        // The seeded World Cup story is tagged Sports + Entertainment.
        Category sports = categoryService.findAll().stream()
                .filter(c -> c.getName().equals("Sports"))
                .findFirst().orElseThrow();

        News worldCup = newsService.findHeadlinesByCategory(sports.getId()).stream()
                .filter(n -> n.getHeadline().contains("World Cup"))
                .findFirst().orElseThrow();

        assertThat(worldCup.getCategories())
                .extracting(Category::getName)
                .contains("Sports", "Entertainment");
    }

    @Test
    void readersCanAddComments() {
        News anyNews = categoryService.findAll().stream()
                .flatMap(c -> newsService.findHeadlinesByCategory(c.getId()).stream())
                .findFirst().orElseThrow();

        int before = newsService.getWithComments(anyNews.getId()).getComments().size();
        newsService.addComment(anyNews.getId(), "Tester", "Great article!");
        int after = newsService.getWithComments(anyNews.getId()).getComments().size();

        assertThat(after).isEqualTo(before + 1);
    }
}
