package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Review;

public interface ReviewService {

	List<Review> rvList();

	int rvInsert(Review review);

}
