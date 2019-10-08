package com.codegym.controllers;

import com.codegym.model.Blog;
import com.codegym.model.BlogJson;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiBlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/blogs")
    public ResponseEntity<Blog> createNewBlog(@RequestBody BlogJson blogJson){
        Category category = categoryService.findById(blogJson.getCategory());

        Blog blog = new Blog();

        blog.setTitle(blogJson.getTitle());
        blog.setContent(blogJson.getContent());
        blog.setCategory(category);
        blogService.save(blog);

        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> listAllBlog(){
        List<Blog> blogList = (List<Blog>) blogService.findAll();

        if (blogList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(blogList, HttpStatus.OK);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> detailBlog(@PathVariable("id") Long id) {
        Blog blog = blogService.findById(id);
        if(blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable("id") Long id) {
        Blog blog = blogService.findById(id);
        if(blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blogService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") Long id, @RequestBody BlogJson blogJson) {
        Blog currentBlog = blogService.findById(id);

        if (currentBlog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = categoryService.findById(blogJson.getCategory());

        currentBlog.setTitle(blogJson.getTitle());
        currentBlog.setContent(blogJson.getContent());
        currentBlog.setCategory(category);

        blogService.save(currentBlog);

        return new ResponseEntity<>(currentBlog, HttpStatus.OK);
    }

}
