package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Review;

@Mapper
public interface ReviewMapper {

	List<Review> rvList();

	int rvInsert(Review review);

}
