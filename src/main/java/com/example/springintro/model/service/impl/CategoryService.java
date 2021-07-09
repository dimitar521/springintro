package com.example.springintro.model.service.impl;

import com.example.springintro.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    public default void seedCategories() throws IOException {
    }

    Set<Category> getRandomCategories();
}
