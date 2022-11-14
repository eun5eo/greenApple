package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Product;

public interface ProductService {

	// 상품 목록
	List<Product> list(Product product);

	// 상품을 제철별로
	List<Product> listSeasonal(String seasonal);

	// 상품 상세보기
	Product view(int productCode);

	int getTotal();

}
