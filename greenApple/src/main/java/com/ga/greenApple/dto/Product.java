package com.ga.greenApple.dto;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Alias("product")
public class Product {
	private String productCode; // 상품 코드
	
	private String thumbnail; // 썸네일
	
	private String fileName; // 파일명
	
	private String productName; // 상품명
	
	private int price; // 가격
	
	private String productDescription; // 상품 설명
	
	private String origin; // 원산지
	
	private String seasonal; // 제철
	
	private int stock; // 재고
	
	private Date inputDate; // 등록일
	
	private String soldOut; // 품절 여부
	
	private String del; // 상품 삭제 여부
	
	// upload용
//	private MultipartFile thumbnailFile;
	
	private MultipartFile files;
	
	// 검색용
	private String keyword; // 검색 단어
	
	// 페이징용
	private int startRow; // 시작페이지
	
	private int endRow; // 끝페이지
	
	// 바로 구매용
	private int amount; // 수량
}
