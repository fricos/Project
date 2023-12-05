package com.kerekegyensuly.project.service;

import com.kerekegyensuly.project.model.Category;
import com.kerekegyensuly.project.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public void createCategory(Category category) {
        categoryRepo.save(category);
    }
}
