package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Product;

@Mapper
public interface ProductMapper {

	// 상품 목록
	List<Product> list(Product product);
	
	// 총 상품 갯수 (페이징)
	int getTotal();

	// 상품을 제철별로
	List<Product> listSeasonal(String seasonal);

	// 상품 상세보기
	Product view(int productCode);

}
