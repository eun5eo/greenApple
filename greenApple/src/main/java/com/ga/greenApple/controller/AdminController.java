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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ga.greenApple.dto.AdminData;
import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.dto.ProductImg;
import com.ga.greenApple.dto.Review;
import com.ga.greenApple.service.AdminService;

@RestController
public class AdminController {
	@Autowired
	private AdminService as;
	
	// 상품 목록
	@PostMapping(value = "/admin/productList")
	public AdminData productList(@RequestBody AdminData data, HttpSession session) {
		String id = (String) session.getAttribute("id");
		
		// 페이징을 위한 값
		final int ROW_PER_PAGE = 5; // 한 페이지에 들어갈 데이터 개수
		final int PAGE_PER_BLOCK = 5; // 한 블럭에 들어갈 페이지수
		
		// 첫 로딩이거나 pageNum에 값이 없으면 페이지값은 1
		if (data.getPageNum() == null || data.getPageNum().equals("")) {
			data.setPageNum("1");
		}
		
		// 현재 페이지
		int currentPage = Integer.parseInt(data.getPageNum());
		
		// 게시글 시작 번호 : (페이지번호 -1) * 페이지당 개수 +1
		int startRow = (currentPage - 1) * ROW_PER_PAGE + 1;
		
		// 게시글 끝 번호 : 시작번호 + 페이지당 개수 -1
		int endRow = startRow + ROW_PER_PAGE - 1;
		
		// 시작 페이지 : 현재 페이지 - (현재 페이지 -1) % 블록당 개수 => 1, 11, 21,..
		int startPage = currentPage - (currentPage - 1) % PAGE_PER_BLOCK;
		
		// 끝 페이지 : 시작 페이지 + 블록당 페이지수 -1
		int endPage = startPage + PAGE_PER_BLOCK - 1;
		
		// 총 데이터수
		int total = as.productTotal(data);
		
		// 총 페이지수
		int totalPage = (int) Math.ceil((double)total/ROW_PER_PAGE);
		
		// 끝 페이지가 총 페이지보다 크면, 끝 페이지는 총 페이지로 변경
		if (endPage > totalPage) endPage = totalPage;
		
		List<Product> productList = null;
		
		if (id.equals("admin")) {
			data.setStartRow(startRow);
			data.setEndRow(endRow);
			
			productList = as.productList(data);
		}
		
		// 리스트랑 페이징 요소 한 번에 보내기
		AdminData adminData = new AdminData();
		adminData.setProductList(productList);
		adminData.setCurrentPage(currentPage);
		adminData.setStartPage(startPage);
		adminData.setStartRow(startRow);
		adminData.setEndPage(endPage);
		adminData.setEndRow(endRow);
		adminData.setTotalPage(totalPage);
		
		return adminData;
	}
	
	// 상품 등록
	@PostMapping(value = "/admin/productInsert")
	public int productInsert(@ModelAttribute Product product, MultipartHttpServletRequest mhr, 
			HttpSession session) throws IOException {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		String realPath = "/home/uploads/";
		
		// productCode 생성
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("yyMMddHHmmss");
		String nowDate = fm.format(date);
		
		// 한 번에 여러 장의 파일을 받는다
		List<MultipartFile> fileList = mhr.getFiles("files");
		// 여러 장의 파일을 각각 담을 공간 생성
		List<ProductImg> pdPhotos = new ArrayList<ProductImg>();
		
		if (id.equals("admin")) {
			// 썸네일 파일 저장 (사진 전송 시 순서 유지 어려움으로 썸네일은 따로 처리)
			MultipartFile thumbnailFile = mhr.getFile("thumbnailFile");
			FileOutputStream fos1 = new FileOutputStream(
					new File(realPath+"/"+thumbnailFile.getOriginalFilename()));
			fos1.write(thumbnailFile.getBytes());
			fos1.close();
			
			// list의 사진을 하나씩 가져와 pdPhotos에 저장
			for (MultipartFile mf2 : fileList) {
				ProductImg pi = new ProductImg();
				String fileName = mf2.getOriginalFilename();
				pi.setFileName(fileName);
				
				// productImg의 갯수는 사진의 갯수만큼
				pdPhotos.add(pi);
				
				// 그림 파일 저장
				FileOutputStream fos2 = new FileOutputStream(new File(realPath+"/"+fileName));
				fos2.write(mf2.getBytes());
				fos2.close();
				
				// product 테이블에도 그림을 넣어줘야 등록된다
				product.setThumbnail(product.getThumbnailFile().getOriginalFilename());
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
		List<MultipartFile> fileList = mhr.getFiles("files");
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
				
				// 썸네일 파일 저장 (사진 전송 시 순서 유지 어려움으로 썸네일은 따로 처리)
				MultipartFile thumbnailFile = mhr.getFile("thumbnailFile");
				FileOutputStream fos1 = new FileOutputStream(
						new File(realPath+"/"+thumbnailFile.getOriginalFilename()));
				fos1.write(thumbnailFile.getBytes());
				fos1.close();
				
				// list의 사진을 하나씩 가져와 pdPhotos에 저장
				for (MultipartFile mf2 : fileList) {
					String fileName = mf2.getOriginalFilename();
					ProductImg pi = new ProductImg();
					pi.setFileName(fileName);
					
					// productImg의 갯수는 사진의 갯수만큼
					pdPhotos.add(pi);
					
					// 그림 파일 저장
					FileOutputStream fos2 = new FileOutputStream(new File(realPath+"/"+fileName));
					fos2.write(mf2.getBytes());
					fos2.close();
					
					// product 테이블에도 그림을 넣어줘야 등록된다
					product.setThumbnail(product.getThumbnailFile().getOriginalFilename());
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
	@PostMapping(value = "/admin/productDelete")
	public int productDelete(@RequestBody Product product, HttpSession session) {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		
		if (id.equals("admin")) {
			result = as.pdDelete(product.getProductCode());
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
	public AdminData memberList(@RequestBody AdminData data, HttpSession session) {		
		String id = (String) session.getAttribute("id");
		
		// 페이징을 위한 값
		final int ROW_PER_PAGE = 10; // 한 페이지에 들어갈 데이터 개수
		final int PAGE_PER_BLOCK = 5; // 한 블럭에 들어갈 페이지수
		
		// 첫 로딩이거나 pageNum에 값이 없으면 페이지값은 1
		if (data.getPageNum() == null || data.getPageNum().equals("")) {
			data.setPageNum("1");
		}
		
		// 현재 페이지
		int currentPage = Integer.parseInt(data.getPageNum());
		
		// 게시글 시작 번호 : (페이지번호 -1) * 페이지당 개수 +1
		int startRow = (currentPage - 1) * ROW_PER_PAGE + 1;
		
		// 게시글 끝 번호 : 시작번호 + 페이지당 개수 -1
		int endRow = startRow + ROW_PER_PAGE - 1;
		
		// 시작 페이지 : 현재 페이지 - (현재 페이지 -1) % 블록당 개수 => 1, 11, 21,..
		int startPage = currentPage - (currentPage - 1) % PAGE_PER_BLOCK;
		
		// 끝 페이지 : 시작 페이지 + 블록당 페이지수 -1
		int endPage = startPage + PAGE_PER_BLOCK - 1;
		
		// 총 데이터수
		int total = as.memberTotal(data);
		
		// 총 페이지수
		int totalPage = (int) Math.ceil((double)total/ROW_PER_PAGE);
		
		// 끝 페이지가 총 페이지보다 크면, 끝 페이지는 총 페이지로 변경
		if (endPage > totalPage) endPage = totalPage;
		
		List<Member> memberList = null;
		
		if (id.equals("admin")) {
			data.setStartRow(startRow);
			data.setEndRow(endRow);
			
			memberList = as.memberList(data);
		}
		
		// 리스트랑 페이징 요소 한 번에 보내기
		AdminData adminData = new AdminData();
		adminData.setMemberList(memberList);
		adminData.setCurrentPage(currentPage);
		adminData.setStartPage(startPage);
		adminData.setStartRow(startRow);
		adminData.setEndPage(endPage);
		adminData.setEndRow(endRow);
		adminData.setTotalPage(totalPage);
		
		return adminData;
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
	public AdminData reviewList(@RequestBody AdminData data, HttpSession session) {
		String id = (String) session.getAttribute("id");
		
		// 페이징을 위한 값
		final int ROW_PER_PAGE = 10; // 한 페이지에 들어갈 데이터 개수
		final int PAGE_PER_BLOCK = 5; // 한 블럭에 들어갈 페이지수
		
		// 첫 로딩이거나 pageNum에 값이 없으면 페이지값은 1
		if (data.getPageNum() == null || data.getPageNum().equals("")) {
			data.setPageNum("1");
		}
		
		// 현재 페이지
		int currentPage = Integer.parseInt(data.getPageNum());
		
		// 게시글 시작 번호 : (페이지번호 -1) * 페이지당 개수 +1
		int startRow = (currentPage - 1) * ROW_PER_PAGE + 1;
		
		// 게시글 끝 번호 : 시작번호 + 페이지당 개수 -1
		int endRow = startRow + ROW_PER_PAGE - 1;
		
		// 시작 페이지 : 현재 페이지 - (현재 페이지 -1) % 블록당 개수 => 1, 11, 21,..
		int startPage = currentPage - (currentPage - 1) % PAGE_PER_BLOCK;
		
		// 끝 페이지 : 시작 페이지 + 블록당 페이지수 -1
		int endPage = startPage + PAGE_PER_BLOCK - 1;
		
		// 총 데이터수
		int total = as.reviewTotal(data);
		
		// 총 페이지수
		int totalPage = (int) Math.ceil((double)total/ROW_PER_PAGE);
		
		// 끝 페이지가 총 페이지보다 크면, 끝 페이지는 총 페이지로 변경
		if (endPage > totalPage) endPage = totalPage;
		
		List<Review> reviewList = null;
		
		if (id.equals("admin")) {
			data.setStartRow(startRow);
			data.setEndRow(endRow);
			
			reviewList = as.reviewList(data);
		}
		
		// 리스트랑 페이징 요소 한 번에 보내기
		AdminData adminData = new AdminData();
		adminData.setReviewList(reviewList);
		adminData.setCurrentPage(currentPage);
		adminData.setStartPage(startPage);
		adminData.setStartRow(startRow);
		adminData.setEndPage(endPage);
		adminData.setEndRow(endRow);
		adminData.setTotalPage(totalPage);
		
		return adminData;
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
