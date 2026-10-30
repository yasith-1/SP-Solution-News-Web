package com.spsolutions.newsweb.service.impl;

import com.spsolutions.newsweb.entity.Category;
import com.spsolutions.newsweb.exception.ResourceNotFoundException;
import com.spsolutions.newsweb.repository.CategoryRepository;
import com.spsolutions.newsweb.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * JPA-backed implementation of {@link CategoryService}.
 *
 * <p>SRP: this class only coordinates category persistence; it holds no HTTP or view logic.
 * It depends on the {@link CategoryRepository} abstraction (constructor injection), satisfying
 * Dependency Inversion.</p>
 */
@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Category", id));
    }
}
