package com.begin.bg.services;

import com.begin.bg.models.Product;
import com.begin.bg.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(int id){
        return productRepository.findById(id);
    }

    public List<Product> findProductByName(String name){
        return productRepository.findByName(name);
    }

    public Boolean productExistsById(int id){
        return productRepository.existsById(id);
    }

    public void deleteProductById(int id){
        productRepository.deleteById(id);
    }
}
