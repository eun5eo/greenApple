package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("orderDetail")
public class OrderDetail {
	private int detailNo; // 주문 상세 번호
	private int orderNo; // 주문 번호 fk
	private int productCode; // 상품 코드
	private int amount; // 수량
	private int price; // 가격
}
