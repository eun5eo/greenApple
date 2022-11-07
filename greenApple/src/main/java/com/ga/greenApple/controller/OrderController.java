package com.ga.greenApple.controller;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Cart;
import com.ga.greenApple.dto.Order;
import com.ga.greenApple.dto.OrderDetail;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.service.CartService;
import com.ga.greenApple.service.OrderService;
import com.ga.greenApple.service.ProductService;

@RestController
public class OrderController {
	@Autowired
	private OrderService os;
	
	@Autowired
	private ProductService ps;
	
	@Autowired
	private CartService cs;

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
		String id = session.getId();
		
		for(Order order : orders) {
			order.setId(id);
			os.orderInsert(order);
		}
		
		for(OrderDetail detail : details) {
			detail.setOrderId(0);
			os.orderDetailInsert(detail);
		}
		
		
		return result;
	}
	
}
