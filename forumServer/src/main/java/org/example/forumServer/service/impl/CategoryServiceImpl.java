package org.example.forumServer.service.impl;

import org.example.forumServer.constant.CommonCst;
import org.example.forumServer.dao.ICategoryDao;
import org.example.forumServer.model.Category;
import org.example.forumServer.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    ICategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findOneCategoryById(Integer categoryId) {
        return categoryDao.findOneCategoryById(categoryId);
    }

    @Override
    public Boolean addCategory(Category category) {
        return categoryDao.addCategory(category) > 0 ? true : false;
    }

    @Override
    public Boolean updateCategory(Category category) {
        return categoryDao.updateCategory(category) > 0 ? true : false;
    }

    //更新分类下帖子数量
    @Override
    public Boolean updateCount(Integer categoryId, Integer option) {
        Category category = this.findOneCategoryById(categoryId);
        if (option == CommonCst.COUNT_INCREASE) {
            category.setTopicCount(category.getTopicCount() + 1);
        } else if (option == CommonCst.COUNT_DECREASE) {
            category.setTopicCount(category.getTopicCount() - 1);
        } else {
            return false;
        }
        return categoryDao.updateCount(category) > 0 ? true : false;
    }

    @Override
    public Boolean delCategory(Integer categoryId) {
        return categoryDao.delCategory(categoryId) > 0 ? true : false;
    }
}
