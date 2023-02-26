package com.limeira.democacheredis.dtos;

import java.io.Serializable;

import com.limeira.democacheredis.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String description;
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		description = entity.getDescription();
	}
	
}
