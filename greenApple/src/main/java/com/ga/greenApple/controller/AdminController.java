package com.ga.greenApple.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;
import com.ga.greenApple.dto.Review;
import com.ga.greenApple.service.AdminService;

@RestController
public class AdminController {
	@Autowired
	private AdminService as;
	
	// 상품 등록
	@PostMapping(value = "/admin/productInsert")
	public int productInsert(@ModelAttribute Product product, MultipartHttpServletRequest mhr, 
			HttpSession session) throws IOException {
		int result = 0;
		
		// 한 번에 여러 장의 파일을 받는다
		List<MultipartFile> list = mhr.getFiles("file");
		List<ProductImg> pdPhotos = new ArrayList<ProductImg>();
		
		String realPath = "src/main/resources/static/pdImages";
		
		// list의 사진을 하나씩 가져와 pdPhotos에 저장
		for (MultipartFile mf : list) {
			ProductImg pi = new ProductImg();
			String fileName = mf.getOriginalFilename();
			pi.setFileName(fileName);
			
			// productImg의 갯수는 사진의 갯수만큼
			pdPhotos.add(pi);
			
			// 그림 파일 저장
			FileOutputStream fos = new FileOutputStream(new File(realPath+"/"+fileName));
			fos.write(mf.getBytes());
			fos.close();
			
			// product 테이블에도 그림을 넣어줘야 등록된다
			product.setFileName(fileName);
		}
		result = as.pdInsert(product);
		
		if (result > 0) {
			int productCode = product.getProductCode();
			as.insertPhotos(pdPhotos, productCode);
		}
		
		return result;
	}
	
	// 상품 수정
	@PostMapping(value = "/admin/productUpdate")
	public int productUpdate(@ModelAttribute Product product, HttpSession session) 
			throws IOException {
		int result = 0;
		String id = (String) session.getAttribute("id");
		
		if (id.equals("admin")) {
			String fileName = product.getFile().getOriginalFilename();
			
			// 수정 시 새 파일이 들어오지 않았다면, 이전의 파일을 가져와서 등록
			if (fileName != null && !fileName.equals("")) {
				product.setFileName(fileName);
				
				String realPath = "src/main/resources/static/pdImages";
				FileOutputStream fos = new FileOutputStream(new File(realPath+"/"+fileName));
				
				fos.write(product.getFile().getBytes());
				fos.close();
			}
			result = as.pdUpdate(product);
		} else result = -1;
		
		return result;
	}
	
	// 상품 삭제
	@RequestMapping(value = "/admin/productDelete")
	public int productDelete(@RequestParam("productCode") int productCode,
			HttpSession session) {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		
		if (id.equals("admin")) {
			result = as.pdDelete(productCode);
		} else result = -1;
		
		return result;
	}
	
	// 상품 품절
	@PostMapping(value = "/admin/productSoldOut")
	public int productSoldOut(@RequestBody Product product, HttpSession session) {
		int result = 0;
		String id = (String) session.getAttribute("id");
		
		if (id.equals("admin")) {
			result = as.productSoldOut(product.getProductCode());
		}
		
		return result;
	}
	
	// 회원 목록
	@PostMapping(value = "/admin/memberList")
	public List<Member> memberList(HttpSession session) {		
		String id = (String) session.getAttribute("id");
		
		List<Member> memberList = null;
		
		if (id.equals("admin")) {
			memberList = as.memberList();
		}
		
		return memberList;
	}
	
	// 회원 탈퇴 처리
	@PostMapping(value = "/admin/memberDelete")
	public int memberDelete(@RequestBody Member member, HttpSession session) {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		
		if (id.equals("admin")) {
			result = as.memberDelete(member.getId());
		}
		
		return result;
	}
	
	// 리뷰 목록
	@PostMapping(value = "/admin/reviewList")
	public List<Review> reviewList(HttpSession session) {
		String id = (String) session.getAttribute("id");
		
		List<Review> reviewList = null;
		
		if (id.equals("admin")) {
			reviewList = as.reviewList();
		}
		
		return reviewList;
	}
	
	// 리뷰 삭제 처리
	@PostMapping(value = "/admin/reviewDelete")
	public int reviewDelete(@RequestParam("reviewId") String reviewId, HttpSession session) {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		
		if (id.equals("admin")) {
			result = as.reviewDelete(reviewId);
		}
		
		return result;
	}
}
