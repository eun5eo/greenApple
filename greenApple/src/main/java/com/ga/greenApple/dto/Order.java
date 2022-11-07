package com.ga.greenApple.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Alias("productOrder")
public class Order {
	private String orderId; // 주문 아이디
	
	private String id; // 아이디 fk
	
	private Date orderDate; // 주문 날짜
	
	private String address1; // 주소
	
	private String address2; // 상세 주소
	
	private String recipient; // 수령자 이름
	
	private int recipientTel; // 수령자 전화번호
	
	// product
	private String productName; // 상품명
	
	private int price; // 가격
	
	// 받을 정보
	private int amount; // 주문 수량
	
	private int totalPrice;

}
