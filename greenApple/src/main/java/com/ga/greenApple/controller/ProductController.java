package com.ga.greenApple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Product;
import com.ga.greenApple.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService ps;
	
	// 상품 목록
	@RequestMapping(value = "/product")
	public List<Product> productList() {
		List<Product> list = ps.list();
			
		return list;
	}
	
	// 상품을 제철별로
	@RequestMapping(value = "/product/seasonal/{seasonal}") 
	public List<Product> listSeasonal(@PathVariable String seasonal) { 
		List<Product> listSeasonal = ps.listSeasonal(seasonal);
		
		return listSeasonal; 
	}
	
	// 상품 상세보기
	@RequestMapping(value = "/product/view/{productCode}")
	public Product view(@PathVariable int productCode) {
		Product product = ps.view(productCode);
		
		return product;
	}
	
}
