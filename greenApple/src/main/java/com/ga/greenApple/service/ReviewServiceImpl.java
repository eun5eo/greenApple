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

	@Override
	public List<Review> rvList(int productCode) {
		return rm.rvList(productCode);
	}
	
	@Override
	public List<ReviewImg> imgList(int reviewNo) {
		return rm.imgList(reviewNo);
	}

	@Override
	public int rvInsert(Review review) {
		return rm.rvInsert(review);
	}

	@Override
	public void insertPhotos(List<ReviewImg> rvPhotos) {
		// list에 들어있는 rvPhotos를 ri에 넣어 각각 작업하며 하나씩 처리
		for (ReviewImg ri : rvPhotos) {
			rm.insertRvPhoto(ri);
		}
	}

	@Override
	public int rvUpdate(Review review) {
		return rm.rvUpdate(review);
	}

	@Override
	public int rvDelete(int reviewNo) {
		return rm.rvDelete(reviewNo);
	}

}
