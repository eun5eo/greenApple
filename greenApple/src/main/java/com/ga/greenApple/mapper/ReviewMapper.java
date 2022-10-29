package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;

@Mapper
public interface ReviewMapper {

	List<Review> rvList();

	int rvInsert(Review review);

	void insertRvPhoto(ReviewImg ri);

}
