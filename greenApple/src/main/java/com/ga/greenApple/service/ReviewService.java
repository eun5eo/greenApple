package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;

public interface ReviewService {

	// 리뷰 리스트
	List<Review> rvList(String productCode);
	
	// reviewNo에 따른 이미지
	List<ReviewImg> imgList(String reviewId);
	
	// 리스트나 뷰에 쓰일 리뷰 갯수
	int reviewNum(String productCode);

	// 리뷰 작성
	int rvInsert(Review review);

	// 리뷰 작성 (사진)
	void insertPhotos(List<ReviewImg> rvPhotos, String reviewId, String id);

	// 리뷰 수정/삭제 시 작성자 id 찾기 (비교용)
	String findWriterId(String reviewId);
	
	// 리뷰 수정
	int rvUpdate(Review review);

	// 리뷰 삭제
	int rvDelete(String reviewId);

}
