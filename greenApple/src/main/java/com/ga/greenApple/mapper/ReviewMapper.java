package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;

@Mapper
public interface ReviewMapper {

	// 리뷰 리스트
	List<Review> reviewList(String productCode);
	
	// reviewNo에 따른 이미지
	List<ReviewImg> reviewImgList(String reviewId);
	
	// 리스트나 뷰에 쓰일 리뷰 갯수
	int reviewNum(String productCode);

	// 리뷰 작성
	int reviewInsert(Review review);

	// 리뷰 작성 (사진)
	void insertRvPhoto(ReviewImg ri);
	
	// 리뷰 수정/삭제 시 작성자 id 찾기 (비교용)
	String findWriteId(String reviewId);
	
	// 리뷰 수정 (이미지 변경 없을 시)
	int reviewUpdateNoImg(Review review);

	// 리뷰 수정
	int reviewUpdate(Review review);
	
	// 리뷰 이미지 삭제
	int reviewImgDelete(String reviewId);

	// 리뷰 삭제
	int reviewDelete(String reviewId);

}
