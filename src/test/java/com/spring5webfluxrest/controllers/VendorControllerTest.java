package com.spring5webfluxrest.controllers;

import com.spring5webfluxrest.domain.Vendor;
import com.spring5webfluxrest.repositories.VendorRepository;
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

public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {

        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("John").lastName("Rambo").build(),
                        Vendor.builder().firstName("Chuck").lastName("Norris").build()));

        webTestClient.get()
                .uri("/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById("someID"))
                .willReturn(Mono.just(Vendor.builder().firstName("Nick").lastName("Nolte").build()));

        webTestClient.get()
                .uri("/vendors/someID")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void  testCreateVendor(){
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().firstName("Chuck").firstName("Norris").build()));

        Mono<Vendor> manSaveMono= Mono.just(Vendor.builder().firstName("fName").lastName("lName").build());

        webTestClient.post()
                .uri("/vendors")
                .body(manSaveMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdateVendor(){
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> manSaveMono= Mono.just(Vendor.builder().firstName("fName").lastName("lName").build());

        webTestClient.put()
                .uri("vendors/someid")
                .exchange()
                .expectStatus()
                .isOk();
    }
}