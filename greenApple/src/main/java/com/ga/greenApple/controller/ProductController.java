package com.ga.greenApple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Product;
import com.ga.greenApple.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService ps;
	
	@RequestMapping("/product")
	public List<Product> productList() {
		List<Product> list = ps.list();
		
		return list;
	}
}
