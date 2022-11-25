package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Product;

public interface ProductService {

	// 상품 목록
	List<Product> list(Product product);

	// 총 상품 갯수 (페이징)
	int getTotal();
	
	// 상품을 제철별로
	List<Product> listSeasonal(String seasonal);

	// 상품 상세보기
	Product view(String productCode);

	// 바로구매하려는 상품 정보 결제창에 보여주기
	Product nowOrder(Product product);

}
