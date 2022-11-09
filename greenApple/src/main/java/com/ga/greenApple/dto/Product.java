package com.ga.greenApple.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("product")
public class Product {
	private int productCode; // 상품 코드
	
	private String thumbnail; // 썸네일
	
	private String productName; // 상품명
	
	private int price; // 가격
	
	private String productDescription; // 상품 설명
	
	private String origin; // 원산지
	
	private String seasonal; // 제철
	
	private int stock; // 재고
	
	private Date inputDate; // 등록일
	
	// 검색용
	private String search;
	
	private String keyword;
}
