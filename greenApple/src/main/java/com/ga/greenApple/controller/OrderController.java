package com.ga.greenApple.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.dto.OrderDetail;
import com.ga.greenApple.service.CartService;
import com.ga.greenApple.service.OrderService;
import com.ga.greenApple.service.ProductService;

@RestController
public class OrderController {
	@Autowired
	private OrderService os;

	// 회원별 주문 목록
	@PostMapping(value = "/order/orderList")
	public List<Order> orderList(HttpServletRequest request) {
		String id = request.getSession().getId();
		
		List<Order> orderList = os.orderList(id);
		
		return orderList;
	}
	
	// 주문 등록
	@PostMapping(value = "/order/orderInsert")
	public int orderInsert(@RequestBody List<Order> orders, @RequestBody List<OrderDetail> details,
			HttpSession session) {
		int result = 0;
		String id = (String) session.getAttribute("id");
		
		// 현재 시간으로 orderId 생성
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");		
		String nowDate = fm.format(date) + (int)(Math.random() * 10);
				
		// List로 받은 orders를 하나씩 나누기
		for(Order order : orders) {
			order.setOrderId(nowDate);
			order.setId(id);
			
			result = os.orderInsert(order);
		}
		
		// List로 받은 details를 하나씩 나누기
		for(OrderDetail detail : details) {
			detail.setOrderId(nowDate);
			
			os.orderDetailInsert(detail);
			
			if (result > 0) {
				detail.setId(id);
				
				os.stockDown(detail);
				os.deleteCart(detail);
			}
		}
		
		return result;
	}
	
}
