package org.example.forumServer.controller;

import org.example.forumServer.model.Category;
import org.example.forumServer.service.ICategoryService;
import org.example.forumServer.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @RequestMapping("/findall")
    public JsonResult findAll() {
        JsonResult result = new JsonResult();
        result.setSuccess();
        List<Category> categories = categoryService.findAll();
        result.addDatas("categoryList", categories);
        return result;
    }

    @RequestMapping("/delcategory")
    public JsonResult delCategory(Integer categoryId) {
        JsonResult result = new JsonResult();
        if (categoryService.delCategory(categoryId)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/updatecategory")
    public JsonResult updateCategory(@RequestBody Category category) {
        JsonResult result = new JsonResult();
        if (categoryService.updateCategory(category)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/addcategory")
    public JsonResult addCategory(@RequestBody Category category) {
        JsonResult result = new JsonResult();
        if (categoryService.addCategory(category)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }
}
