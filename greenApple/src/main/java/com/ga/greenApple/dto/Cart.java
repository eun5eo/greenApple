package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("cart")
public class Cart {
	private int cartNo; // 카트 번호
	
	private String id; // 아이디 fk
	
	private String productCode; // 상품 코드
	
	private int amount; // 수량
	
	private int checkStatus; // 카트에서의 체크 여부
	
	// product에서 꺼내줄 정보
	private String productName; // 상품명
	
	private String thumbnail; // 상품 사진
	
	private int price; // 가격
	
}
