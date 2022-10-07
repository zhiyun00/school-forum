package org.example.forumServer.dao;

import org.example.forumServer.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryDao {
    List<Category> findAll();

    Category findOneCategoryById(Integer categoryId);

    Integer addCategory(Category category);

    Integer updateCategory(Category category);

    Integer updateCount(Category category);

    Integer delCategory(Integer categoryId);
}
