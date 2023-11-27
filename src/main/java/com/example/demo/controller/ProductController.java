package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.brocker.ProcessorService;
import com.example.demo.models.ProductModel;
import com.example.demo.repositories.ProductRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProcessorService service;

    public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.findAll());
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/api/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found.");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(productO.get());
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody 
        @Valid ProductRecordDto productRecordDto) {

        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto,productModel);
        service.execute();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(productModel));
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found.");
        }
        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product deleted successfully.");
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found.");
        }
        var productModel = productO.get();
        BeanUtils.copyProperties(productRecordDto,
                productModel);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.save(productModel));
    }

}
