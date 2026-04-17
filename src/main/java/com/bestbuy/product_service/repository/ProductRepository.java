package com.bestbuy.product_service.repository;

import com.bestbuy.product_service.model.Product;
import com.bestbuy.product_service.model.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(ProductCategory category);
    
}
