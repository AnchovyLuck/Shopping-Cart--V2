package com.springweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.springweb.exception.NotEnoughProductsInStockException;
import com.springweb.model.Product;
import com.springweb.model.paging.CurrentPageTransporter;
import com.springweb.service.ProductService;
import com.springweb.service.ShoppingCartService;

@Controller
public class ShoppingCartController  {
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/shoppingCart")
	public ModelAndView shoppingCart() {
		ModelAndView modelAndView = new ModelAndView("shoppingCart");
		modelAndView.addObject("products", shoppingCartService.getProductsInCart());
		modelAndView.addObject("total", shoppingCartService.getTotal());
		System.out.println("Model AND View" + modelAndView);
		return modelAndView;
	}
	
	@GetMapping("/shoppingCart/addProduct/{productId}")
	public String addProductToCartStayCurrentPage(@PathVariable("productId") Long productId) {
		System.out.println(productId);
		productService.findById(productId).ifPresent(shoppingCartService::addProduct);
		int qsCurrentPage = CurrentPageTransporter.getCurrentPage();
		System.out.println("-------------------GET qsCurrentPage---------- = " + qsCurrentPage);
		
		return "redirect:/products" + "?page=" + qsCurrentPage;
	}
	
	@GetMapping("/shoppingCart/removeProduct/{productId}")
	public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
		productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
		return shoppingCart();
	}
	
	@GetMapping("/shoppingCart/checkout")
	public ModelAndView checkoutCart() {
		ModelAndView modelAndView = new ModelAndView("shoppingCart");
		modelAndView.addObject("products", shoppingCartService.getProductsInCart());
		modelAndView.addObject("total", shoppingCartService.getTotal().toString());
		
		try {
			shoppingCartService.checkout();
			modelAndView.addObject("success", "You have checked out successfully.");
		} catch (NotEnoughProductsInStockException e) {
			modelAndView.addObject("error", "Sorry, there is not enough quantity for your order.");
			return shoppingCart().addObject("outOfStockMessage", e.getMessage());
		}
		return modelAndView;
	}
}
