/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.begin.bg.controllers;

import com.begin.bg.models.Product;
import com.begin.bg.models.ResponseObject;
import com.begin.bg.repositories.ProductRepository;
import com.begin.bg.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Duong Quoc Thai CE171563
 */
@SpringBootApplication
@RestController
@RequestMapping(path = "/products")
@RequiredArgsConstructor
public class ProductController {

    //DI : Dependency injecttion (create service first)

    private final ProductService service;


    @GetMapping("")
    List<Product> getAllProducts() {
        return service.findAllProducts();
    }

    //Get detail product
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id) {
        Optional<Product> foundProduct = service.findProductById(id);
        return foundProduct.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Product found", foundProduct))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("FAILED", "Cannot find product with id = " + id, null));
    }

    /*
    Product getProductByID(@PathVariable UUID id){
        return service.findById(id).orElseThrow(()->new RuntimeException("ERROR"));
    }
     */

    //Insert new product with POST method
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        List<Product> foundProduct = service.findProductByName(newProduct.getName().trim());

        return foundProduct.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Insert product successful!", service.saveProduct(newProduct)))
                : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("FAIL", "Product name already taken", null));
    }

    //Update product or insert product if not found
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable UUID id) {
        Product updatedProduct = service.findProductById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    return service.saveProduct(product);
                }).orElseGet(() -> {
            return service.saveProduct(newProduct);
        });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Updated product succesful!", null));
    }

    //Delete a product
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable UUID id) {
        Boolean exists = service.productExistsById(id);
        if(exists){
            Product product = service.deleteProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Deleted product with id = " + id + " successful!", product));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("FAIL", "Product not found", ""));
    }
}
