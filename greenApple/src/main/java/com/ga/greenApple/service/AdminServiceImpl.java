package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.AdminData;
import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;
import com.ga.greenApple.dto.Review;
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
	public void insertPhotos(List<ProductImg> pdPhotos, String productCode) {
		// list에 들어있는 pdPhotos를 pi에 넣어 각각 작업하며 하나씩 처리
		for (ProductImg pi : pdPhotos) {
			pi.setProductCode(productCode);
			am.insertPhotos(pi);
		}
	}
	
	// 상품 수정 (이미지 수정 안하는 경우)
	@Override
	public int productUpdateNoImg(Product product) {
		return am.productUpdateNoImg(product);
	}
	
	// 상품 수정 (productImg 지우기)
	@Override
	public int productImgDelete(String productCode) {
		return am.productImgDelete(productCode);
	}
	
	// 상품 수정
	@Override
	public int pdUpdate(Product product) {
		return am.pdUpdate(product);
	}

	// 상품 삭제
	@Override
	public int pdDelete(String productCode) {
		return am.pdDelete(productCode);
	}
	
	// 상품 품절
	@Override
	public int productSoldOut(String productCode) {
		return am.productSoldOut(productCode);
	}
	
	// 페이징 시 총 데이터 개수 구하기
	@Override
	public int getTotal() {
		return am.getTotal();
	}

	// 회원 목록
	@Override
	public List<Member> memberList(AdminData adminData) {
		return am.memberList(adminData);
	}
	
	// 회원 탈퇴 처리
	@Override
	public int memberDelete(String id) {
		return am.memberDelete(id);
	}

	// 리뷰 목록
	@Override
	public List<Review> reviewList(AdminData adminData) {
		return am.reviewList(adminData);
	}

	// 리뷰 삭제
	@Override
	public int reviewDelete(String reviewId) {
		return am.reviewDelete(reviewId);
	}

}
