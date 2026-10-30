package com.spsolutions.newsweb.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // Inverse side of the Many-to-Many relationship.
    // The owning side (with the join table) is News.categories.
    @ManyToMany(mappedBy = "categories")
    private Set<News> newsItems = new HashSet<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<News> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(Set<News> newsItems) {
        this.newsItems = newsItems;
    }
}
