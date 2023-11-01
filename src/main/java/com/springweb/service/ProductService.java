package com.springweb.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springweb.model.Product;

public interface ProductService {
	public Page<Product> findAllProductsPageable(Pageable pageable);
	
	public Optional<Product> findById(Long id);
}
