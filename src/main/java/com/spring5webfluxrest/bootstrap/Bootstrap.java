package com.spring5webfluxrest.bootstrap;

import com.spring5webfluxrest.domain.Category;
import com.spring5webfluxrest.domain.Vendor;
import com.spring5webfluxrest.repositories.CategoryRepository;
import com.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count().block() == 0) {
            //load data
            System.out.println("**** LOADING DATA ON BOOTSTRAP ****");

            loadCategories();
            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            loadVendors();
            System.out.println("Loaded Vendors: " + vendorRepository.count().block());

        }

    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder()
                .firstName("John")
                .lastName("Rambo").build());

        vendorRepository.save(Vendor.builder()
                .firstName("Chuck")
                .lastName("Norris").build());

        vendorRepository.save(Vendor.builder()
                .firstName("Steven")
                .lastName("Segal").build());

        vendorRepository.save(Vendor.builder()
                .firstName("Bruce")
                .lastName("Lee").build());

        vendorRepository.save(Vendor.builder()
                .firstName("Benny")
                .lastName("Hill").build());

        vendorRepository.save(Vendor.builder()
                .firstName("Jaś")
                .lastName("Fasola").build());

        vendorRepository.save(Vendor.builder()
                .firstName("Scarlett")
                .lastName("Johansson").build());

        vendorRepository.save(Vendor.builder()
                .firstName("Emilia")
                .lastName("Clarke").build());
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder()
                .description("Fruits").build());

        categoryRepository.save(Category.builder()
                .description("Nuts").build());

        categoryRepository.save(Category.builder()
                .description("Breads").build());

        categoryRepository.save(Category.builder()
                .description("Meats").build());

        categoryRepository.save(Category.builder()
                .description("Eggs").build());
    }
}
