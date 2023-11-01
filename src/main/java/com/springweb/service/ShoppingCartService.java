package com.springweb.service;

import java.math.BigDecimal;
import java.util.Map;

import com.springweb.exception.NotEnoughProductsInStockException;
import com.springweb.model.Product;

public interface ShoppingCartService {
	public void addProduct(Product product);
	
	public void removeProduct(Product product);
	
	public Map<Product, Integer> getProductsInCart();
	
	public void checkout() throws NotEnoughProductsInStockException;
	
	public BigDecimal getTotal();
}
