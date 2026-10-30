package com.spsolutions.newsweb.service;

import com.spsolutions.newsweb.entity.Category;

import java.util.List;

/**
 * Category read operations.
 *
 * <p>Interface Segregation: callers depend on this small, focused contract rather than a
 * concrete class. Dependency Inversion: the controller depends on this abstraction, not on
 * the JPA-backed implementation.</p>
 */
public interface CategoryService {

    List<Category> findAll();

    Category getById(Long id);
}
