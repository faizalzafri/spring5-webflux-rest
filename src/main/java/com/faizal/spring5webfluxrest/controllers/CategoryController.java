package com.faizal.spring5webfluxrest.controllers;

import com.faizal.spring5webfluxrest.domain.Category;
import com.faizal.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    Flux<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<Void> create(@RequestBody Publisher<Category> categoryPublisher) {
        return categoryRepository.saveAll(categoryPublisher).then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {

        Category category1 = categoryRepository.findById(id).block();

        if (category1.getDescription() != category.getDescription()) {
            category1.setDescription(category.getDescription());
            return categoryRepository.save(category1);
        }
        return Mono.just(category1);
    }


}
