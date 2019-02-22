package com.faizal.spring5webfluxrest.controllers;

import com.faizal.spring5webfluxrest.domain.Category;
import com.faizal.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryController categoryController;
    CategoryRepository categoryRepository;


    @Before
    public void setUp() throws Exception {

        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {

        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Category1").build(),
                        Category.builder().description("Category2").build()));

        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Category1").build()));

        webTestClient.get()
                .uri("/api/v1/categories/testId")
                .exchange()
                .expectBody(Category.class);

    }

    @Test
    public void create() throws Exception {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> category = Mono.just(Category.builder().description("Category1").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(category, Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void update() throws Exception {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> category = Mono.just(Category.builder().description("Category1").build());

        webTestClient.put()
                .uri("/api/v1/categories/someId")
                .body(category, Category.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void patch() throws Exception {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> category = Mono.just(Category.builder().description("Category1").build());

        webTestClient.patch()
                .uri("/api/v1/categories/someId")
                .body(category, Category.class)
                .exchange()
                .expectStatus().isOk();


        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    public void patchWithNoArgs() throws Exception {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> category = Mono.just(Category.builder().build());

        webTestClient.patch()
                .uri("/api/v1/categories/someId")
                .body(category, Category.class)
                .exchange()
                .expectStatus().isOk();


        verify(categoryRepository, never()).save(any());
    }


}