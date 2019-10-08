package com.codegym.controllers;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        categoryService.save(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> listAllCategory(){
        List<Category> categories = (List<Category>) categoryService.findAll();
        if(categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category category){
        Category currentCategory = categoryService.findById(id);

        if (currentCategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentCategory.setCategory(category.getCategory());
        categoryService.save(currentCategory);

        return new ResponseEntity<>(currentCategory, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Category> removeCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);

        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        categoryService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categories/{id}/blogs")
    public ResponseEntity<List<Blog>> detailBlogOfCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);

        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Blog> blogList = (List<Blog>) blogService.findAllByCategory(category);

        return new ResponseEntity<>(blogList, HttpStatus.OK);
    }
}
