package com.spring5webfluxrest.controllers;

import com.spring5webfluxrest.domain.Category;
import com.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {

        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Kot1").build(),
                        Category.builder().description("Kot2").build()));

        webTestClient.get()
                .uri("/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(categoryRepository.findById("someID"))
                .willReturn(Mono.just(Category.builder().description("Kot").build()));

        webTestClient.get()
                .uri("/categories/someID")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void testCreateCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("description").build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some cat").build());

        webTestClient.post()
                .uri("/categories")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catUpdateMono = Mono.just(Category.builder().description("Some cat").build());

        webTestClient.put()
                .uri("/categories/someid")
                .body(catUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}