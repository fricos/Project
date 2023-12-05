package com.kerekegyensuly.project.controller;

import com.kerekegyensuly.project.common.ApiResponse;
import com.kerekegyensuly.project.model.Category;
import com.kerekegyensuly.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Uj kategoria letrehozva"), HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public List<Category> listCategory() { return categoryService.listCategory(); }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category){
        System.out.println("category id " + categoryId);
        if (categoryService.findById(categoryId)){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "A kategoria nem letezik"), HttpStatus.OK);
        }
        categoryService.editCategory(categoryId, category);
        return new ResponseEntity<>(new ApiResponse(true, "A kategoria mar letre lett hozva."), HttpStatus.OK);
    }
}
