package com.faizal.spring5webfluxrest.controllers;

import com.faizal.spring5webfluxrest.domain.Vendor;
import com.faizal.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorController vendorController;

    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {

        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstname("Vendor1").build(),
                        Vendor.builder().firstname("Vendor2").build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstname("Vendor1").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/testId")
                .exchange()
                .expectBody(Vendor.class);

    }
}