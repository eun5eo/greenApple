package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Order;

public interface OrderService {

	List<Order> memberOrderList(String id);

}
