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

	@Override
	public List<Product> list() {
		return pm.list();
	}

	@Override
	public List<Product> listSeasonal(String seasonal) {
		return pm.listSeasonal(seasonal);
	}
}
