package com.ga.greenApple.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;
import com.ga.greenApple.service.ReviewService;

@RestController
public class ReviewController {	
	@Autowired
	private ReviewService rs;
	
	// 리뷰 리스트
	@RequestMapping(value = "/review/list/{productCode}")
	public List<Review> reviewList(@PathVariable int productCode) {
		List<Review> rvList = rs.rvList(productCode);
		
		return rvList;
	}
	
	// reviewNo에 따른 이미지
	@RequestMapping(value = "/review/imgList")
	public List<ReviewImg> reviewImg(@RequestParam("reviewNo") int reviewNo) {
		List<ReviewImg> imgList = rs.imgList(reviewNo);
		
		return imgList;
	}
	
	// 리뷰 작성
	@PostMapping(value = "/review/insert")
	public int reviewInsert(@ModelAttribute Review review, MultipartHttpServletRequest mhr, 
			HttpSession session) throws IOException {
		int result = 0;
		String id = (String) session.getAttribute("id");
		review.setId(id);
			
		// 한 번에 여러 장의 파일을 받는다
		List<MultipartFile> list = mhr.getFiles("file");
		List<ReviewImg> rvPhotos = new ArrayList<ReviewImg>();
		
		String real = "src/main/resources/static/rvImages";
		
		// list의 사진을 하나씩 가져와 rvPhotos에 저장
		for (MultipartFile mf : list) {
			ReviewImg ri = new ReviewImg();
			String fileName = mf.getOriginalFilename();
			ri.setFileName(fileName);
			ri.setId(id);
			
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
	
	// 리뷰 수정
	@PostMapping(value = "/review/update")
	public int reviewUpdate(@RequestBody Review review, HttpSession session) 
			throws IOException {
		int result = 0;
		
		String fileName = review.getFile().getOriginalFilename();
		
		if (fileName != null && !fileName.equals("")) {
			review.setFileName(fileName);
			String real = "src/main/resources/static/rvImages";
			FileOutputStream fos = new FileOutputStream(new File(real+"/"+fileName));
			fos.write(review.getFile().getBytes());
			fos.close();
		}
		
		result = rs.rvUpdate(review);
		
		return result;
	}
	
	// 리뷰 삭제
	@PostMapping(value = "/review/delete")
	public int  reviewDelete(@RequestBody int reviewNo) {
		int result = rs.rvDelete(reviewNo);
		
		return result;
	}
}
