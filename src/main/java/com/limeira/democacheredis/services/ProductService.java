package com.limeira.democacheredis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.limeira.democacheredis.dtos.ProductDTO;
import com.limeira.democacheredis.entities.Product;
import com.limeira.democacheredis.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	@Autowired
	ProductRepository repository;
	
	@Cacheable("products")
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {
		log.info("Get All Products ...");
		for (int i = 0; i < 1000000; i++) 
			for (int x = 0; x < 1000; x++) ;
		log.info("Get All Products");
		System.out.println("Get All Products");
		return repository.findAll().stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}	
	
	@CacheEvict(value = "products", allEntries = true)
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		log.info("Insert Product...");
		Product entity = new Product();
		entity.setDescription(dto.getDescription());
		entity = repository.save(entity);
		dto.setId(entity.getId());
		return dto;
	}	

}
