package com.spring5webfluxrest.controllers;

import com.spring5webfluxrest.domain.Category;
import com.spring5webfluxrest.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping("/categories")
    Flux<Category> list(){
        return categoryRepository.findAll();
    }

    @GetMapping("/categories/{id}")
    Mono<Category>getById(@PathVariable String id){
        return categoryRepository.findById(id);
    }
}
