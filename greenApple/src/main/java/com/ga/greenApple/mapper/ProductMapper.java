package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Product;

@Mapper
public interface ProductMapper {

	List<Product> list();

	List<Product> listSeasonal(String seasonal);

}
