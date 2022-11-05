package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.dto.OrderDetail;

public interface OrderService {

	List<Order> memberOrderList(String id);

	int orderInsert(Order order);

	int orderDetailInsert(OrderDetail detail);

}
