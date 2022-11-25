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
	public List<Review> reviewList(@PathVariable String productCode) {
		List<Review> reviewList = rs.reviewList(productCode);
		
		return reviewList;
	}
	
	// reviewId에 따른 이미지
	@RequestMapping(value = "/review/imgList")
	public List<ReviewImg> reviewImg(@RequestParam("reviewId") String reviewId) {
		List<ReviewImg> reviewImgList = rs.reviewImgList(reviewId);
		
		return reviewImgList;
	}
	
	// 리스트나 뷰에 쓰일 리뷰 갯수
	@RequestMapping(value = "/review/reviewNum")
	public int reviewNum(@RequestParam("productCode") String productCode) {
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
		List<MultipartFile> list = mhr.getFiles("files");
		List<ReviewImg> rvPhotos = new ArrayList<ReviewImg>();
		
		// 사진 파일이 들어온 경우
		if (review.getFiles() == null) {
			String realPath = "src/main/resources/static/rvImages";
			
			// list의 사진을 하나씩 가져와 rvPhotos에 저장
			for (MultipartFile mf : list) {
				ReviewImg ri = new ReviewImg();
				String fileName = mf.getOriginalFilename();
				ri.setFileName(fileName);
				ri.setId(id);
				
				// reviewImg의 갯수는 사진의 갯수만큼
				rvPhotos.add(ri);
				
				// 그림 파일 저장
				FileOutputStream fos = new FileOutputStream(new File(realPath+"/"+fileName));
				fos.write(mf.getBytes());
				fos.close();
				
				// review 테이블에도 그림을 넣어줘야 등록된다
				review.setFileName(fileName);
				
			}
			result = rs.reviewInsert(review);
		
		// 사진 파일이 들어오지 않은 경우 (리뷰 글만)
		} else if (review.getFiles() != null) {
			String fileName = "n";
			review.setFileName(fileName);
			
			// result 값을 다르게 하여 reviewImg에 입력하지 않게 한다
			result = rs.reviewInsert(review) + 1;
		}
		
		if (result == 1) {
			String reviewId = nowDate;
			
			rs.insertPhotos(rvPhotos, reviewId, id);
		}
		
		return result;
	}
	
	// 리뷰 수정
	@PostMapping(value = "/review/update")
	public int reviewUpdate(@ModelAttribute Review review, MultipartHttpServletRequest mhr,
			HttpSession session) throws IOException {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		
		// 한 번에 여러 장의 파일을 받는다
		List<MultipartFile> list = mhr.getFiles("files");
		List<ReviewImg> rvPhotos = new ArrayList<ReviewImg>();
		
		String fileName = ((MultipartFile) review.getFiles()).getOriginalFilename();
		String realPath = "src/main/resources/static/rvImages";
		
		// 수정 시 새 파일이 들어오지 않았다면, 이전의 파일을 가져와서 등록
		if (review.getFiles() == null) {
			review.setFileName(fileName);
			
			FileOutputStream fos = new FileOutputStream(new File(realPath+"/"+fileName));
			
			fos.write(((MultipartFile) review.getFiles()).getBytes());
			fos.close();
			
			result = rs.reviewUpdate(review);
			
		// 수정 시 새 파일이 들어온 경우, 원래의 사진을 지우고 새로 업로드
		} else if (review.getFiles() != null) {
			// 기존에 등록된 이미지를 지운다
			result = rs.reviewImgDelete(review.getReviewId());
			
			// list의 사진을 하나씩 가져와 rvPhotos에 저장
			for (MultipartFile mf : list) {
				ReviewImg ri = new ReviewImg();
				fileName = mf.getOriginalFilename();
				ri.setFileName(fileName);
				
				// reviewImg의 갯수는 사진의 갯수만큼
				rvPhotos.add(ri);
				
				// 그림 파일 저장
				FileOutputStream fos = new FileOutputStream(new File(realPath+"/"+fileName));
				fos.write(mf.getBytes());
				fos.close();
				
				// review 테이블에도 그림을 넣어줘야 등록된다
				review.setFileName(fileName);
			}
			result = rs.reviewUpdate(review);
			
			if (result == 2) {
				String reviewId = review.getReviewId();
				
				rs.insertPhotos(rvPhotos, reviewId, id);
			}
		}
		
		return result;
	}
	
	// 리뷰 삭제
	@RequestMapping(value = "/review/delete")
	public int  reviewDelete(@RequestParam("reviewId") String reviewId,
			HttpSession session) {
		int result = 0;

		result = rs.reviewDelete(reviewId);
		
		return result;
	}
}
