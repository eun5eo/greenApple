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
	public List<Review> reviewList(String productCode) {
		return rm.reviewList(productCode);
	}
	
	// reviewNo에 따른 이미지
	@Override
	public List<ReviewImg> reviewImgList(String reviewId) {
		return rm.reviewImgList(reviewId);
	}
	
	// 리스트나 뷰에 쓰일 리뷰 갯수
	@Override
	public int reviewNum(String productCode) {
		return rm.reviewNum(productCode);
	}

	// 리뷰 작성
	@Override
	public int reviewInsert(Review review) {
		return rm.reviewInsert(review);
	}

	// 리뷰 작성 (사진)
	@Override
	public void insertPhotos(List<ReviewImg> rvPhotos, String reviewId, String id) {
		// list에 들어있는 rvPhotos를 ri에 넣어 각각 작업하며 하나씩 처리
		for (ReviewImg ri : rvPhotos) {
			ri.setReviewId(reviewId);
			ri.setId(id);
			
			rm.insertRvPhoto(ri);
		}
	}
	
	// 리뷰 수정/삭제 시 작성자 id 찾기 (비교용)
	@Override
	public String findWriterId(String reviewId) {
		return rm.findWriteId(reviewId);
	}
	
	// 리뷰 수정 (이미지 변경 없을 시)
	@Override
	public int reviewUpdateNoImg(Review review) {
		return rm.reviewUpdateNoImg(review);
	}

	// 리뷰 수정
	@Override
	public int reviewUpdate(Review review) {
		return rm.reviewUpdate(review);
	}
	
	// 리뷰 이미지 삭제
	@Override
	public int reviewImgDelete(String reviewId) {
		return rm.reviewImgDelete(reviewId);
	}

	// 리뷰 삭제
	@Override
	public int reviewDelete(String reviewId) {
		return rm.reviewDelete(reviewId);
	}

}
