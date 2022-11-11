package com.ga.greenApple.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	// reviewId에 따른 이미지
	@RequestMapping(value = "/review/imgList")
	public List<ReviewImg> reviewImg(@RequestParam("reviewId") String reviewId) {
		List<ReviewImg> imgList = rs.imgList(reviewId);
		
		return imgList;
	}
	
	// 리스트나 뷰에 쓰일 리뷰 갯수
	@RequestMapping(value = "/review/reviewNum")
	public int reviewNum(@RequestParam("productCode") int productCode) {
		int reviewNum = rs.reviewNum(productCode);
				
		return reviewNum;
	}
	
	// 리뷰 작성
	@PostMapping(value = "/review/insert")
	public int reviewInsert(@ModelAttribute Review review, MultipartHttpServletRequest mhr, 
			HttpSession session) throws IOException {
		int result = 0;
		
		// 현재 시간으로 reviewId 생성
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");		
		String nowDate = fm.format(date) + (int)(Math.random() * 10);
		
		String id = (String) session.getAttribute("id");
		review.setReviewId(nowDate);
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
		
		if (result > 0) {
			String reviewId = review.getReviewId();
			rs.insertPhotos(rvPhotos, reviewId);
		}
		
		return result;
	}
	
	// 리뷰 수정
	@PostMapping(value = "/review/update")
	public int reviewUpdate(@ModelAttribute Review review, HttpSession session) 
			throws IOException {
		int result = 0;
		String requestId = rs.findWriterId(review.getReviewId());
		String sessionId = (String) session.getAttribute("id");
		
		if (sessionId == requestId) {
			String fileName = review.getFile().getOriginalFilename();
			
			// 파일이 들어오지 않았다면, 이전의 것을 등록
			if (fileName != null && !fileName.equals("")) {
				review.setFileName(fileName);
				
				String real = "src/main/resources/static/rvImages";
				FileOutputStream fos = new FileOutputStream(new File(real+"/"+fileName));
				
				fos.write(review.getFile().getBytes());
				fos.close();
			}
			result = rs.rvUpdate(review);
		} else result = -1; // id 일치 x
		
		return result;
	}
	
	// 리뷰 삭제
	@RequestMapping(value = "/review/delete")
	public int  reviewDelete(@RequestParam("reviewId") String reviewId,
			HttpSession session) {
		int result = 0;
		String requestId = rs.findWriterId(reviewId);
		String sessionId = (String) session.getAttribute("id");
		
		if (requestId == sessionId) {
			result = rs.rvDelete(reviewId);
		} else result = -1;
		
		return result;
	}
}
