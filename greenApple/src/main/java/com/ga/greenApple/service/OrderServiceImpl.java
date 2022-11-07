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

	@Override
	public List<Order> orderList(String id) {
		return om.orderList(id);
	}

	@Override
	public int orderInsert(Order order) {
		return om.orderInsert(order);
	}

	@Override
	public int orderDetailInsert(OrderDetail detail) {
		return om.orderDetailInsert(detail);
	}
	
}
