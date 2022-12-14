package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.dto.OrderDetail;

public interface OrderService {

	// 회원별 주문 목록
	List<Order> orderList(String id);
	
	// 주문 상세 목록
	List<OrderDetail> detailList(String orderId);

	// 주문 등록
	int orderInsert(Order order);

	// 주문 등록 (주문 상세)
	int orderDetailInsert(OrderDetail detail);

	// 주문 등록된 상품 수량 하향
	void stockDown(OrderDetail detail);

	// 주문 등록된 상품 카트에서 삭제
	void deleteCart(OrderDetail detail);

	// 주문 취소
	int orderDelete(String orderId);

	// 주문 상세 취소
	void detailDelete(String orderId);

}
