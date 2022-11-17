package com.ga.greenApple.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.dto.OrderDetail;
import com.ga.greenApple.dto.Product;
import com.ga.greenApple.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService os;

	// 회원별 주문 내역 목록
	@PostMapping(value = "/order/orderList")
	public List<Order> orderList(HttpServletRequest request) {
		String id = request.getSession().getId();
		
		List<Order> orderList = os.orderList(id);
		
		return orderList;
	}
	
	// 주문 상세 목록
	@PostMapping(value = "/order/orderDetailList")
	public List<OrderDetail> orderDetailList(@RequestBody Order order, HttpSession session) {
		String orderId = order.getOrderId();
		List<OrderDetail> detailList = os.detailList(orderId);
		
		return detailList;
	}
	
	// 주문 등록
	@PostMapping(value = "/order/orderInsert")
	public int orderInsert(@RequestBody Order order, HttpSession session) {
		int result = 0;
		String id = (String) session.getAttribute("id");
		
		// 현재 시간으로 orderId 생성
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");		
		String nowDate = fm.format(date) + (int)(Math.random() * 10);
		
		order.setOrderId(nowDate);
		order.setId(id);
		
		result = os.orderInsert(order);
		
		// List로 받은 detailList를 하나씩 분리
		if (result > 0) {
			for (OrderDetail detail : order.getDetailList()) {
				detail.setOrderId(nowDate);
				
				int detailResult = os.orderDetailInsert(detail);
				
				if (detailResult > 0) {
					detail.setId(id);
					
					os.stockDown(detail);
					os.deleteCart(detail);
				}
			}
		}
		
		return result;
	}
	
	// 주문 취소
	@PostMapping(value = "/order/orderDelete")
	public int orderDelete(@RequestBody Order order, HttpSession session) {
		int result = os.orderDelete(order.getOrderId());
		
		if (result > 0) {
			os.detailDelete(order.getOrderId());
		}
		
		return result;
	}
	
}
