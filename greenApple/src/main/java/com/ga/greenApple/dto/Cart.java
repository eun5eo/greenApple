package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("cart")
public class Cart {
	private int cartNo; // 카트 번호
	private int productCode; // 상품 코드 fk
	private String id; // 아이디 fk
	private int amount; // 수량
	
	// product
	private String productName; // 상품명
	private int price; // 가격
	
	private int totalPrice;
}
