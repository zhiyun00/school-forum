package org.example.forumServer.service;

import org.example.forumServer.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();

    Category findOneCategoryById(Integer categoryId);

    Boolean addCategory(Category category);

    Boolean updateCategory(Category category);

    Boolean updateCount(Integer categoryId, Integer option);

    Boolean delCategory(Integer categoryId);
}
