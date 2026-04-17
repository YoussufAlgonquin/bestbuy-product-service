package com.bestbuy.product_service.controller;


import com.bestbuy.product_service.model.*;
import com.bestbuy.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController           // @Controller + @ResponseBody — all methods return JSON by default
@RequestMapping("/products")
@CrossOrigin(origins = "*")  // Allow requests from the Vue.js frontends
public class ProductController {
    
    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) ProductCategory category){
        return service.getAllProducts(Optional.ofNullable(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product created = service.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody Product product){
        return ResponseEntity.ok(service.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
