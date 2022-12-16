package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.AdminData;
import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;
import com.ga.greenApple.dto.Review;

public interface AdminService {

	// 상품 목록
	List<Product> productList(AdminData forAdminData);
	
	// 상품 등록
	int pdInsert(Product product);

	// 상품 사진 등록
	void insertPhotos(List<ProductImg> pdPhotos, String productCode);
	
	// 상품 수정 (이미지 수정 안하는 경우)
	int productUpdateNoImg(Product product);
	
	// 상품 수정 (productImg 지우기)
	int productImgDelete(String productCode);
	
	// 상품 수정
	int pdUpdate(Product product);

	// 상품 삭제
	int pdDelete(String productCode);
	
	// 상품 품절
	int productSoldOut(String productCode);

	// 회원 목록
	List<Member> memberList(AdminData forAdminData);
	
	// 회원 탈퇴 처리
	int memberDelete(String id);

	// 리뷰 목록
	List<Review> reviewList(AdminData forAdminData);

	// 리뷰 삭제
	int reviewDelete(String reviewId);
	
	// 페이징 시 총 상품 개수 구하기
	int productTotal();
	
	// 페이징 시 검색한 경우 총 상품 개수 구하기
	int productTotalSearch(AdminData data);
	
	// 페이징 시 총 회원수 구하기
	int memberTotal();
	
	// 페이징 시 검색한 경우 총 회원수 구하기
	int memberTotalSearch(AdminData data);
	
	// 페이징 시 총 리뷰 개수 구하기
	int reviewTotal();
	
	// 페이징 시 검색한 경우 총 리뷰 개수 구하기
	int reviewTotalSearch(AdminData data);

}
