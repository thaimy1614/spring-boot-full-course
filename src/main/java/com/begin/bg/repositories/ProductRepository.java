/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.begin.bg.repositories;

import com.begin.bg.models.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Include many method to get data 
 * @author Duong Quoc Thai CE171563
 */
public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findByName(String productName);

}
