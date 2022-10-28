package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Review;
import com.ga.greenApple.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewMapper rm;

	@Override
	public List<Review> rvList() {
		return rm.rvList();
	}

	@Override
	public int rvInsert(Review review) {
		return rm.rvInsert(review);
	}
}
