package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Product;
import com.ga.greenApple.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper pm;

	// 상품 목록
	@Override
	public List<Product> list(Product product) {
		return pm.list(product);
	}

	// 총 상품 갯수 (페이징)
	@Override
	public int getTotal() {
		return pm.getTotal();
	}

	// 상품을 제철별로
	@Override
	public List<Product> listSeasonal(String seasonal) {
		return pm.listSeasonal(seasonal);
	}

	// 상품 상세보기
	@Override
	public Product view(String productCode) {
		return pm.view(productCode);
	}

	// 바로구매하려는 상품 정보 결제창에 보여주기
	@Override
	public Product nowOrder(Product product) {
		return pm.nowOrder(product);
	}

}
