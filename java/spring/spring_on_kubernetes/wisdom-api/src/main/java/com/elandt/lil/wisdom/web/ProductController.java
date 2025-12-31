package com.elandt.lil.wisdom.web;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.lil.wisdom.data.entity.Product;
import com.elandt.lil.wisdom.data.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

  private final ProductRepository productRepository;

  @GetMapping
  public Iterable<Product> getAllProducts(@RequestParam(required = false) String name) {
    if (StringUtils.hasLength(name)) {
      var products = new ArrayList<Product>();
      var product = this.productRepository.findByName(name);
      if (product.isPresent()) {
        products.add(product.get());
      }
      return products;
    }

    return this.productRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable UUID id) {
    return ResponseEntity.of(this.productRepository.findById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product createProduct(@RequestBody Product entity) {
    return this.productRepository.save(entity);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product entity) {
    if (!id.equals(entity.getProductId())) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ofNullable(this.productRepository.save(entity));
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.RESET_CONTENT)
  public void deleteProduct(@PathVariable UUID id) {
    this.productRepository.deleteById(id);
  }
}
