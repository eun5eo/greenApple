package com.ga.greenApple.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("productOrder")
public class Order {
	private int orderNo; // 주문 번호
	private String id; // 아이디 fk
	private Date orderDate; // 주문 날짜
	private String address; // 주소
	private String recipient; // 수령자 이름
	private int recipientTel; // 수령자 전화번호
}
