package com.springweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springweb.model.Product;
import com.springweb.model.paging.CurrentPageTransporter;
import com.springweb.model.paging.Pager;
import com.springweb.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	private static final int INITIAL_PAGE = 0;
	
	
	
	@GetMapping(value = {"/products"})
	public ModelAndView fetchProducts(@RequestParam("page") Optional<Integer> page) {
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
		
		int currentPage = evalPage + 1;
		CurrentPageTransporter.setCurrentPage(currentPage);
		System.out.println("-----------------SET qsCurrentPage--------------- = " + currentPage);
		
		Sort sort = Sort.by("Name").ascending();
		Page<Product> products = productService.findAllProductsPageable(PageRequest.of(evalPage, 5, sort));
		
		List<Product> listProducts = products.getContent();
		
		listProducts.forEach(product -> System.out.println(product.getId()));
		
		Pager pager = new Pager(products);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("products", products);
		modelAndView.addObject("pager", pager);
		modelAndView.setViewName("/products");
		
		return modelAndView;
	}
}
