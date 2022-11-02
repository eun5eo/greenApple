package com.ga.greenApple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.service.MemberService;
import com.ga.greenApple.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService os;
	
	@Autowired
	private MemberService ms;
	
	// 회원별 주문 목록
	@PostMapping(value = "/order/memberOrderList")
	public List<Order> memOrderList(@RequestBody String id) {
		List<Order> memberOrderList = os.memberOrderList(id);
		
		return memberOrderList;
	}
	
	
}
