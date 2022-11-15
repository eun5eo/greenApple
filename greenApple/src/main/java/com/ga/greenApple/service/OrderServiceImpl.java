package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.dto.OrderDetail;
import com.ga.greenApple.mapper.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper om;

	// 회원별 주문 목록
	@Override
	public List<Order> orderList(String id) {
		return om.orderList(id);
	}

	// 주문 등록
	@Override
	public int orderInsert(Order order) {
		return om.orderInsert(order);
	}

	// 주문 등록 (주문 상세)
	@Override
	public int orderDetailInsert(OrderDetail detail) {
		return om.orderDetailInsert(detail);
	}

	// 주문 등록된 상품 수량 하향
	@Override
	public void stockDown(OrderDetail detail) {
		om.stockDown(detail);
		
	}

	// 주문 등록된 상품 카트에서 삭제
	@Override
	public void deleteCart(OrderDetail detail) {
		om.deleteCart(detail);
		
	}

	// 주문 취소
	@Override
	public int orderDelete(String orderId) {
		return om.orderDelete(orderId);
	}

}
