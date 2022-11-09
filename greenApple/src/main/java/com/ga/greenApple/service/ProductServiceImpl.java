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
	public List<Product> list(String search, String keyword) {
		return pm.list(search, keyword);
	}

	// 상품을 제철별로
	@Override
	public List<Product> listSeasonal(String seasonal) {
		return pm.listSeasonal(seasonal);
	}

	// 상품 상세보기
	@Override
	public Product view(int productCode) {
		return pm.view(productCode);
	}

}
