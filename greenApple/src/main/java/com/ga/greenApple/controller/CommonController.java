package com.ga.greenApple.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.Review;
import com.ga.greenApple.service.MemberService;
import com.ga.greenApple.service.ProductService;
import com.ga.greenApple.service.ReviewService;

@RestController
public class CommonController {	
	@Autowired
	private ReviewService rs;
	
	@Autowired
	private ProductService ps;
	
	@Autowired
	private MemberService ms;
	
	@RequestMapping(value = "/reviewList")
	public List<Review> reviewList() {
		List<Review> rvList = rs.rvList();
		
		return rvList;
	}
	
	@RequestMapping(value = "/reviewInsert")
	public int reviewInsert(@PathVariable Review review) {
		int result = 0;
		
		result = rs.rvInsert(review);
		
		return result;
	}
}
