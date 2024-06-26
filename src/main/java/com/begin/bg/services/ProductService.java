package com.begin.bg.services;

import com.begin.bg.entities.Product;
import com.begin.bg.entities.ProductStatus;
import com.begin.bg.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(UUID id) {
        return productRepository.findById(id);
    }

    public List<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Boolean productExistsById(UUID id) {
        return productRepository.existsById(id);
    }

    public Product deleteProductById(UUID id) {
        Product product = productRepository.findById(id).get();
        product.setStatus(ProductStatus.DELETED);
        return productRepository.save(product);
    }
}
