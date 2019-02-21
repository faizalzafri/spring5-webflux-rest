package com.faizal.spring5webfluxrest.bootstrap;

import com.faizal.spring5webfluxrest.domain.Category;
import com.faizal.spring5webfluxrest.domain.Vendor;
import com.faizal.spring5webfluxrest.repositories.CategoryRepository;
import com.faizal.spring5webfluxrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("#### Started Loading Bootstrap Data ####");
        loadCategory();
        loadVendor();
        log.info("#### Finished Loading Bootstrap Data ####");
    }

    private void loadCategory() {

        if (categoryRepository.count().block() == 0) {
            log.info("#### Loading Categories ####");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();

            log.info("#### Loaded Categories " + categoryRepository.count().block() +
                    " ####");
        }

    }

    private void loadVendor() {

        if (vendorRepository.count().block() == 0) {
            log.info("#### Loading Vendors ####");

            vendorRepository.save(Vendor.builder().firstname("Tony").lastname("Stark").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Steve").lastname("Rogers").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Natasha").lastname("Romanoff").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Thor").lastname("OdinSon").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Bruce").lastname("Banner").build()).block();

            log.info("#### Loaded Vendors " + vendorRepository.count().block() +
                    " ####");
        }

    }
}
