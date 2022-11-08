package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.dto.OrderDetail;

public interface OrderService {

	// 회원별 주문 목록
	List<Order> orderList(String id);

	// 주문 등록
	int orderInsert(Order order);

	// 주문 등록 (주문 상세)
	int orderDetailInsert(OrderDetail detail);

	// 주문 등록된 상품 수량 하향
	void stockDown(OrderDetail detail);

}
