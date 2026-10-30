package com.spsolutions.newsweb.repository;

import com.spsolutions.newsweb.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    // Headlines that belong to a given category, newest first.
    @Query("select n from News n join n.categories c where c.id = :categoryId order by n.publishedAt desc")
    List<News> findByCategoryId(@Param("categoryId") Long categoryId);

    // Fetch a single news item together with its comments in one query.
    @Query("select n from News n left join fetch n.comments where n.id = :id")
    Optional<News> findByIdWithComments(@Param("id") Long id);
}
