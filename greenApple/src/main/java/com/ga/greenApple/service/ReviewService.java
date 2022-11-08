package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;

public interface ReviewService {

	// 리뷰 리스트
	List<Review> rvList(int productCode);
	
	// reviewNo에 따른 이미지
	List<ReviewImg> imgList(int reviewNo);

	// 리뷰 작성
	int rvInsert(Review review);

	// 리뷰 작성 (사진)
	void insertPhotos(List<ReviewImg> rvPhotos, int reviewNo);

	// 리뷰 수정
	int rvUpdate(Review review);

	// 리뷰 삭제
	int rvDelete(int reviewNo);

}
