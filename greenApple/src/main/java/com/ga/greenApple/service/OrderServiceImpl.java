package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.mapper.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper om;

	@Override
	public List<Order> memberOrderList(String id) {
		return om.memberOrderList(id);
	}
}
