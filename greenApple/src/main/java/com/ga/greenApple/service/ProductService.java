package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Product;

public interface ProductService {

	List<Product> list();

	List<Product> listSeasonal(String seasonal);

	Product view(int productCode);

	// orderController에서 요청
	Product getProductInfo(String productName);

	// orderController에서 요청
	void stockDown(Product product2);

}
