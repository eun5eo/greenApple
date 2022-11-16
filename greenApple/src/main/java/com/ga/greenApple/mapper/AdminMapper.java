package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;

@Mapper
public interface AdminMapper {

	// 상품 등록
	int pdInsert(Product product);

	// 상품 사진 등록
	void insertPhotos(ProductImg pi);
	
	// 상품 수정
	int pdUpdate(Product product);

	// 상품 삭제
	int pdDelete(int productCode);

	// 회원 목록
	List<Member> memberList();

}
