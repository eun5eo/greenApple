package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("cart")
public class Cart {
	private int cartNo; // 장바구니 번호
	private int productCode; // 상품 코드 fk
	private String id; // 아이디 fk
	private int amount; // 수량
}
