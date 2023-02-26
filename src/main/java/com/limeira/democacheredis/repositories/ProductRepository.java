package com.limeira.democacheredis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limeira.democacheredis.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
