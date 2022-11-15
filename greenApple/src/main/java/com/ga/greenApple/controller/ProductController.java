package com.ga.greenApple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Product;
import com.ga.greenApple.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService ps;
	
	// 상품 목록
	@RequestMapping(value = "/product")
	public List<Product> productList(@RequestParam("keyword") String keyword,
			@RequestParam("page") int page) {
		Product product = new Product();
		
		List<Product> list;
		
		if (keyword != null || keyword != "") {
			product.setKeyword(keyword);
			
			list = ps.list(product);
		}
		
		int rowPerPage = 8; // 한 번 로드할 때 뜨는 상품 수
		int startRow = (page -1) * rowPerPage +1;	// 시작 상품 페이지
		int endRow = startRow + rowPerPage -1;	// 끝 상품 
		
		product.setStartRow(startRow);
		product.setEndRow(endRow);
		
		list = ps.list(product);
		
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
