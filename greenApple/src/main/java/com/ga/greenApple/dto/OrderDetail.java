package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("orderDetail")
public class OrderDetail {
	private int detailNo; // 주문 상세 번호
	
	private String orderId; // 주문 아이디 fk
	
	private String productCode; // 상품 코드
	
	private int amount; // 수량
	
	private int price; // 가격
	
	private String detailDel; // 삭제여부
	
	// 주문 성공 시 cart delete용
	private String id; // 회원 아이디
}
