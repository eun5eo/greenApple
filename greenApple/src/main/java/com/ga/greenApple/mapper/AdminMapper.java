package com.ga.greenApple.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;

@Mapper
public interface AdminMapper {

	// 상품 등록
	int pdInsert(Product product);

	// 상품 사진 등록
	void insertPhotos(ProductImg pi);

}
