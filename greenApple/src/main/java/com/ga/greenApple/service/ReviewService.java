package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;

public interface ReviewService {

	List<Review> rvList();

	int rvInsert(Review review);

	void insertPhotos(List<ReviewImg> rvPhotos);

}
