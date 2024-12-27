package com.elandt.lil.hplus.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.elandt.lil.hplus.data.Product;
import com.elandt.lil.hplus.data.ProductRepository;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryMapping
    public Iterable<Product> products() {
        return this.productRepository.findAll();
    }
}
