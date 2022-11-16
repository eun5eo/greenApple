package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;

public interface AdminService {

	// 상품 등록
	int pdInsert(Product product);

	// 상품 사진 등록
	void insertPhotos(List<ProductImg> pdPhotos, int productCode);
	
	// 상품 수정
	int pdUpdate(Product product);

	// 상품 삭제
	int pdDelete(int productCode);

	// 회원 목록
	List<Member> memberList();

}
