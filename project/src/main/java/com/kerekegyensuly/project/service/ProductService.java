package com.kerekegyensuly.project.service;

import com.kerekegyensuly.project.dto.product.ProductDto;
import com.kerekegyensuly.project.model.Category;
import com.kerekegyensuly.project.model.Product;
import com.kerekegyensuly.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> listProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products){
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }
    public static ProductDto getDtoFromProduct(Product product){
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        return product;
    }

    public void addProduct(ProductDto productDto, Category category){
        Product product = getProductFromDto(productDto,category);
        productRepository.save(product);
    }
}