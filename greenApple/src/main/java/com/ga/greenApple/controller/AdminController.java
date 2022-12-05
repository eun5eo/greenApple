package com.ga.greenApple.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
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

import com.ga.greenApple.dto.AdminData;
import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;
import com.ga.greenApple.dto.Review;
import com.ga.greenApple.dto.ReviewImg;
import com.ga.greenApple.service.AdminService;

@RestController
public class AdminController {
	@Autowired
	private AdminService as;
	
	// 상품 목록
	@PostMapping(value = "/admin/productList")
	public List<Product> productList(@RequestParam("keyword") String keyword,
			@RequestParam("tag") String tag, @RequestParam("pageNum") String pageNum,
			HttpSession session) {
		String id = (String) session.getAttribute("id");
		
		// 페이징을 위한 값
		final int ROW_PER_PAGE = 10; // 한 페이지에 들어갈 데이터 개수
		final int PAGE_PER_BLOCK = 5; // 한 블럭에 들어갈 페이지수
		
		// 첫 로딩이거나 pageNum에 값이 없으면 페이지값은 1
		if (pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}
		
		// 현재 페이지
		int currentPage = Integer.parseInt(pageNum);
		// 게시글 시작 번호 : (페이지번호 -1) * 페이지당 개수 +1
		int startRow = (currentPage - 1) * ROW_PER_PAGE + 1;
		// 게시글 끝 번호 : 시작번호 + 페이지당 개수 -1
		int endRow = startRow + ROW_PER_PAGE - 1;
		// 시작 페이지 : 현재 페이지 - (현재 페이지 -1) % 블록당 개수 => 1, 11, 21,..
		int startPage = currentPage - (currentPage - 1) % PAGE_PER_BLOCK;
		// 끝 페이지 : 시작 페이지 + 블록당 페이지수 -1
		int endPage = startPage + PAGE_PER_BLOCK - 1;
		// 총 데이터수
		int total = as.getTotal();
		// 총 페이지수
		int totalPage = (int) Math.ceil((double)total/ROW_PER_PAGE);
		// 끝 페이지가 총 페이지보다 크면, 끝 페이지는 총 페이지로 변경
		if (endPage > totalPage) endPage = totalPage;
		
		// 목록을 보여주기 위해 필요한 데이터들 담기
		AdminData forAdminData = new AdminData();
		forAdminData.setTag(tag);
		forAdminData.setKeyword(keyword);
		forAdminData.setStartRow(startRow);
		forAdminData.setEndRow(endRow);
		
		List<Product> productList = null;
		
		if (id.equals("admin")) {
			productList = as.productList(forAdminData);
		}
		
		return productList;
	}
	
	// 상품 등록
	@PostMapping(value = "/admin/productInsert")
	public int productInsert(@ModelAttribute Product product, MultipartHttpServletRequest mhr, 
			HttpSession session) throws IOException {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		String realPath = "src/main/resources/static/pdImages";
		
		// productCode 생성
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("yyMMddHHmmss");
		String nowDate = fm.format(date);
		
		// 한 번에 여러 장의 파일을 받는다
		List<MultipartFile> list = mhr.getFiles("file");
		// 여러 장의 파일을 각각 담을 공간 생성
		List<ProductImg> pdPhotos = new ArrayList<ProductImg>();
		
		if (id.equals("admin")) {
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
				product.setProductCode(nowDate);
			}
			result = as.pdInsert(product);
			
			// 상품 등록이 완료되면 상품이미지 테이블에 사진 등록
			if (result > 0) {
				String productCode = product.getProductCode();
				as.insertPhotos(pdPhotos, productCode);
			}
		} else result = -1;
		
		return result;
	}
	
	// 상품 수정
	@PostMapping(value = "/admin/productUpdate")
	public int productUpdate(@ModelAttribute Product product, MultipartHttpServletRequest mhr,
			HttpSession session) 
			throws IOException {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		String realPath = "src/main/resources/static/pdImages";
		
		// 한 번에 여러 장의 파일을 받는다
		List<MultipartFile> list = mhr.getFiles("file");
		// 여러 장의 파일을 각각 담을 공간 생성
		List<ProductImg> pdPhotos = new ArrayList<ProductImg>();
		
		if (id.equals("admin")) {
			// 수정 시 새 파일이 들어오지 않았다면, 사진 제외하고 내용만 변경
			if (product.getFiles() == null) {
				
				result = as.productUpdateNoImg(product);
				
			// 수정 시 새 파일이 들어온 경우, 원래의 사진을 지우고 새로 업로드
			} else if (product.getFiles() != null) {
				// 기존에 등록된 이미지를 지운다
				result = as.productImgDelete(product.getProductCode());
				
				// list의 사진을 하나씩 가져와 pdPhotos에 저장
				for (MultipartFile mf : list) {
					String fileName = mf.getOriginalFilename();
					ProductImg pi = new ProductImg();
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
				// 사진 재등록을 위해 result값을 다르게 한다
				result = as.pdUpdate(product) + 1;
				
				// 상품 수정(이미지 포함의 경우)이 완료되면 product_img에 사진 입력
				if (result == 2) {
					String productCode = product.getProductCode();
					
					as.insertPhotos(pdPhotos, productCode);
				}
			}
		} else result = -1;
		
		return result;
	}
	
	// 상품 삭제
	@RequestMapping(value = "/admin/productDelete")
	public int productDelete(@RequestParam("productCode") String productCode,
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
		} else result = -1;
		
		return result;
	}
	
	// 회원 목록
	@PostMapping(value = "/admin/memberList")
	public List<Member> memberList(@RequestParam("keyword") String keyword,
			@RequestParam("tag") String tag, @RequestParam("pageNum") String pageNum,
			HttpSession session) {		
		String id = (String) session.getAttribute("id");
		
		// 페이징을 위한 값
		final int ROW_PER_PAGE = 10; // 한 페이지에 들어갈 데이터 개수
		final int PAGE_PER_BLOCK = 5; // 한 블럭에 들어갈 페이지수
		
		// 첫 로딩이거나 pageNum에 값이 없으면 페이지값은 1
		if (pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}
		
		// 현재 페이지
		int currentPage = Integer.parseInt(pageNum);
		// 게시글 시작 번호 : (페이지번호 -1) * 페이지당 개수 +1
		int startRow = (currentPage - 1) * ROW_PER_PAGE + 1;
		// 게시글 끝 번호 : 시작번호 + 페이지당 개수 -1
		int endRow = startRow + ROW_PER_PAGE - 1;
		// 시작 페이지 : 현재 페이지 - (현재 페이지 -1) % 블록당 개수 => 1, 11, 21,..
		int startPage = currentPage - (currentPage - 1) % PAGE_PER_BLOCK;
		// 끝 페이지 : 시작 페이지 + 블록당 페이지수 -1
		int endPage = startPage + PAGE_PER_BLOCK - 1;
		// 총 데이터수
		int total = as.getTotal();
		// 총 페이지수
		int totalPage = (int) Math.ceil((double)total/ROW_PER_PAGE);
		// 끝 페이지가 총 페이지보다 크면, 끝 페이지는 총 페이지로 변경
		if (endPage > totalPage) endPage = totalPage;
		
		// 목록을 보여주기 위해 필요한 데이터들 담기
		AdminData forAdminData = new AdminData();
		forAdminData.setTag(tag);
		forAdminData.setKeyword(keyword);
		forAdminData.setStartRow(startRow);
		forAdminData.setEndRow(endRow);
		
		List<Member> memberList = null;
		
		if (id.equals("admin")) {
			memberList = as.memberList(forAdminData);
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
		} else result = -1;
		
		return result;
	}
	
	// 리뷰 목록
	@PostMapping(value = "/admin/reviewList")
	public List<Review> reviewList(@RequestParam("keyword") String keyword,
			@RequestParam("tag") String tag, @RequestParam("pageNum") String pageNum,
			HttpSession session) {
		String id = (String) session.getAttribute("id");
		
		// 페이징을 위한 값
		final int ROW_PER_PAGE = 10; // 한 페이지에 들어갈 데이터 개수
		final int PAGE_PER_BLOCK = 5; // 한 블럭에 들어갈 페이지수
		
		// 첫 로딩이거나 pageNum에 값이 없으면 페이지값은 1
		if (pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}
		
		// 현재 페이지
		int currentPage = Integer.parseInt(pageNum);
		// 게시글 시작 번호 : (페이지번호 -1) * 페이지당 개수 +1
		int startRow = (currentPage - 1) * ROW_PER_PAGE + 1;
		// 게시글 끝 번호 : 시작번호 + 페이지당 개수 -1
		int endRow = startRow + ROW_PER_PAGE - 1;
		// 시작 페이지 : 현재 페이지 - (현재 페이지 -1) % 블록당 개수 => 1, 11, 21,..
		int startPage = currentPage - (currentPage - 1) % PAGE_PER_BLOCK;
		// 끝 페이지 : 시작 페이지 + 블록당 페이지수 -1
		int endPage = startPage + PAGE_PER_BLOCK - 1;
		// 총 데이터수
		int total = as.getTotal();
		// 총 페이지수
		int totalPage = (int) Math.ceil((double)total/ROW_PER_PAGE);
		// 끝 페이지가 총 페이지보다 크면, 끝 페이지는 총 페이지로 변경
		if (endPage > totalPage) endPage = totalPage;
		
		// 목록을 보여주기 위해 필요한 데이터들 담기
		AdminData forAdminData = new AdminData();
		forAdminData.setTag(tag);
		forAdminData.setKeyword(keyword);
		forAdminData.setStartRow(startRow);
		forAdminData.setEndRow(endRow);
		
		List<Review> reviewList = null;
		
		if (id.equals("admin")) {
			reviewList = as.reviewList(forAdminData);
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
		} else result = -1;
		
		return result;
	}
}
