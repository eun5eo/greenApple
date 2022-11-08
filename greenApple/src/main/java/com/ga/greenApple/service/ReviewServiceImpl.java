package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;
import com.ga.greenApple.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewMapper rm;

	// 리뷰 리스트
	@Override
	public List<Review> rvList(int productCode) {
		return rm.rvList(productCode);
	}
	
	// reviewNo에 따른 이미지
	@Override
	public List<ReviewImg> imgList(String reviewId) {
		return rm.imgList(reviewId);
	}

	// 리뷰 작성
	@Override
	public int rvInsert(Review review) {
		return rm.rvInsert(review);
	}

	// 리뷰 작성 (사진)
	@Override
	public void insertPhotos(List<ReviewImg> rvPhotos, String reviewId) {
		// list에 들어있는 rvPhotos를 ri에 넣어 각각 작업하며 하나씩 처리
		for (ReviewImg ri : rvPhotos) {
			ri.setReviewId(reviewId);
			rm.insertRvPhoto(ri);
		}
	}

	// 리뷰 수정
	@Override
	public int rvUpdate(Review review) {
		return rm.rvUpdate(review);
	}

	// 리뷰 삭제
	@Override
	public int rvDelete(String reviewId) {
		return rm.rvDelete(reviewId);
	}

}
