package com.bestbuy.product_service.service;

import com.bestbuy.product_service.exception.ProductNotFoundException;
import com.bestbuy.product_service.model.*;
import com.bestbuy.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service  // Marks this as a Spring-managed bean
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts(Optional<ProductCategory> category) {
        return category
            .map(repository::findByCategory)
            .orElse(repository.findAll());
    }

    public Product getProductById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(String id, Product updated) {
        Product existing = getProductById(id);

        existing.setName(updated.getName());
        existing.setBrand(updated.getBrand());
        existing.setCategory(updated.getCategory());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setImageUrl(updated.getImageUrl());

        if (updated.getSpecs() != null) {
            existing.setSpecs(updated.getSpecs());
        }
        return repository.save(existing);
    }


    public void deleteProduct(String id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
