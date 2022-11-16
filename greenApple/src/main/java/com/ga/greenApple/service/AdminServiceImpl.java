package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;
import com.ga.greenApple.mapper.AdminMapper;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper am;

	// 상품 등록
	@Override
	public int pdInsert(Product product) {
		return am.pdInsert(product);
	}

	// 상품 사진 등록
	@Override
	public void insertPhotos(List<ProductImg> pdPhotos, int productCode) {
		// list에 들어있는 pdPhotos를 pi에 넣어 각각 작업하며 하나씩 처리
		for (ProductImg pi : pdPhotos) {
			pi.setProductCode(productCode);
			am.insertPhotos(pi);
		}
	}
}
