package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Product;

@Mapper
public interface ProductMapper {

	List<Product> list();

	List<Product> listSeasonal(String seasonal);

	Product view(int productCode);

	// orderController에서 요청
	Product getProductInfo(String productName);

	// orderController에서 요청
	void stockDown(Product product2);

}
