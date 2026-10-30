package com.spsolutions.newsweb.repository;

import com.spsolutions.newsweb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByNameAsc();

    boolean existsByNameIgnoreCase(String name);
}
