package com.spsolutions.newsweb.config;

import com.spsolutions.newsweb.entity.Category;
import com.spsolutions.newsweb.entity.Comment;
import com.spsolutions.newsweb.entity.News;
import com.spsolutions.newsweb.repository.CategoryRepository;
import com.spsolutions.newsweb.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Seeds a few categories and news items on first startup so the site is not empty.
 * Runs only when the database has no categories yet.
 */
@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Bean
    CommandLineRunner seedData(CategoryRepository categoryRepository, NewsRepository newsRepository) {
        return args -> {
            if (categoryRepository.count() > 0) {
                log.info("Data already present; skipping seed");
                return;
            }
            log.info("Empty database detected; seeding categories and news");

            Category sports = new Category("Sports");
            Category entertainment = new Category("Entertainment");
            Category politics = new Category("Politics");
            Category technology = new Category("Technology");
            Category business = new Category("Business");
            categoryRepository.saveAll(java.util.List.of(sports, entertainment, politics, technology, business));

            // A single news item related to multiple categories (the example from the brief).
            News worldCup = new News(
                    "Sri Lanka lost the Cricket World Cup final",
                    "In a dramatic finish at the stadium, Sri Lanka fell just short in the Cricket World Cup "
                            + "final tonight. Despite a spirited batting effort and strong support from fans across "
                            + "the island, the team could not chase down the target in the closing overs. "
                            + "Celebrations and heartbreak spilled over into streets and living rooms alike, making "
                            + "this as much an entertainment moment as a sporting one.");
            worldCup.addCategory(sports);
            worldCup.addCategory(entertainment);

            News budget = new News(
                    "Government unveils new national budget for 2026",
                    "The finance ministry presented the 2026 national budget today, outlining spending plans "
                            + "across health, education and infrastructure. Analysts say the proposals aim to balance "
                            + "economic recovery with public welfare. Markets responded cautiously in early trading.");
            budget.addCategory(politics);
            budget.addCategory(business);

            News aiChip = new News(
                    "Local startup launches AI chip for edge devices",
                    "A Colombo-based startup announced a new low-power AI chip designed for smartphones and "
                            + "IoT devices. The company claims the chip delivers faster on-device inference while "
                            + "consuming significantly less energy than competing solutions.");
            aiChip.addCategory(technology);
            aiChip.addCategory(business);

            News concert = new News(
                    "Open-air music festival draws record crowd",
                    "Tens of thousands of music lovers gathered over the weekend for the country's largest "
                            + "open-air festival. Headline acts performed late into the night as organisers reported "
                            + "a record turnout.");
            concert.addCategory(entertainment);

            News tournament = new News(
                    "National football league kicks off new season",
                    "The national football league began its new season with several high-profile transfers "
                            + "already making headlines. Clubs are optimistic about larger crowds this year.");
            tournament.addCategory(sports);

            // Seed a couple of example comments.
            worldCup.addComment(new Comment("Nimal", "What a match! Proud of the team regardless."));
            worldCup.addComment(new Comment("Sanduni", "Heartbreaking finish, but a great tournament overall."));

            newsRepository.saveAll(
                    java.util.List.of(worldCup, budget, aiChip, concert, tournament));
            log.info("Seeding complete: {} categories, {} news items",
                    categoryRepository.count(), newsRepository.count());
        };
    }
}
