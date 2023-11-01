package com.springweb.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.springweb.exception.NotEnoughProductsInStockException;
import com.springweb.model.Product;
import com.springweb.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ProductRepository productRepository;

	private Map<Product, Integer> products = new HashMap<>();

	@Override
	public void addProduct(Product product) {
		if (products.containsKey(product)) {
			products.replace(product, products.get(product) + 1);
		} else {
			products.put(product, 1);
		}
	}

	@Override
	public void removeProduct(Product product) {
		if (products.containsKey(product)) {
			products.replace(product, products.get(product) - 1);
		} else {
			products.remove(product);
		}
	}

	@Override
	public Map<Product, Integer> getProductsInCart() {
		return Collections.unmodifiableMap(products);
	}

	@Override
	public void checkout() throws NotEnoughProductsInStockException {
		Product product;
		for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			product = productRepository.getReferenceById(entry.getKey().getId());

			if (product.getQuantity() < entry.getValue())
				throw new NotEnoughProductsInStockException(product);

			entry.getKey().setQuantity(product.getQuantity() - entry.getValue());

			productRepository.saveAll(products.keySet());
			productRepository.flush();
		}
	}

	@Override
	public BigDecimal getTotal() {
		return products.entrySet().stream()
				.map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
				.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

}
