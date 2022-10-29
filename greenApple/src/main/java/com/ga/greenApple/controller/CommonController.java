package com.ga.greenApple.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;
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
	public int reviewInsert(@PathVariable Review review, MultipartHttpServletRequest mhr)
			throws IOException {
		int result = 0;
		
		// 한 번에 여러 장의 파일을 받는다
		List<MultipartFile> list = mhr.getFiles("file");
		List<ReviewImg> rvPhotos = new ArrayList<ReviewImg>();
		
		String real = "src/main/resources/static/images";
		
		// list의 사진을 하나씩 가져와 rvPhotos에 저장
		for (MultipartFile mf : list) {
			ReviewImg ri = new ReviewImg();
			String fileName = mf.getOriginalFilename();
			ri.setFileName(fileName);
			ri.setId(review.getId());
			
			// reviewimg의 갯수는 사진 갯수만큼
			rvPhotos.add(ri);
			
			// 그림 파일 저장
			FileOutputStream fos = new FileOutputStream(new File(real+"/"+fileName));
			fos.write(mf.getBytes());
			fos.close();
			
			// review 테이블에도 그림을 넣어줘야 등록된다
			review.setFileName(fileName);
		}
		
		result = rs.rvInsert(review);
		
		if (result > 0) rs.insertPhotos(rvPhotos);
		
		return result;
	}
}
