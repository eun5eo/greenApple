package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;
import com.ga.greenApple.dto.Review;

@Mapper
public interface AdminMapper {

	// 상품 등록
	int pdInsert(Product product);

	// 상품 사진 등록
	void insertPhotos(ProductImg pi);
	
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
	List<Member> memberList(String keyword, String tag);
	
	// 회원 탈퇴 처리
	int memberDelete(String id);

	// 리뷰 목록
	List<Review> reviewList(String keyword, String tag);

	// 리뷰 삭제
	int reviewDelete(String reviewId);

}
