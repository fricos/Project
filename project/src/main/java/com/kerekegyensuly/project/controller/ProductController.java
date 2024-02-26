package com.kerekegyensuly.project.controller;

import com.kerekegyensuly.project.common.ApiResponse;
import com.kerekegyensuly.project.dto.product.ProductDto;
import com.kerekegyensuly.project.model.Category;
import com.kerekegyensuly.project.service.CategoryService;
import com.kerekegyensuly.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> body = productService.listProducts();
        return new ResponseEntity<List<ProductDto>>(body, HttpStatus.OK);
    }
    @PostMapping("/update/{productID}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID, @RequestBody @Valid ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is not valid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productID, productDto, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }
}
