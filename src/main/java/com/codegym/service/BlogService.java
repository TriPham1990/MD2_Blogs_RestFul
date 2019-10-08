package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;

public interface BlogService {
    Iterable<Blog> findAllByCategory(Category category);

    Iterable<Blog> findAll();

    Blog findById(Long id);

    void save(Blog blog);

    void remove(Long id);
}
