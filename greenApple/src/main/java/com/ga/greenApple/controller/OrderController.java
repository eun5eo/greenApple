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
	
	// 회원별 주문 목록
	@PostMapping(value = "/order/memberOrderList")
	public List<Order> memOrderList(@RequestBody String id) {
		List<Order> memberOrderList = os.memberOrderList(id);
		
		return memberOrderList;
	}
	
	// 장바구니 - 확인 - 결제 - 주문 완료
	@PostMapping(value = "/order/payment")
	public int payment(@RequestBody Order order) {
		int result = 0;
		
		return result;
	}
	
	// 바로 구매 - 확인 - 결제 - 주문 완료
}
