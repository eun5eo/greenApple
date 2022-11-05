package com.ga.greenApple.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	@PostMapping(value = "/order/memberOrderList")
	public List<Order> memOrderList(HttpServletRequest request) {
		String id = request.getSession().getId();
		List<Order> memberOrderList = os.memberOrderList(id);
		
		return memberOrderList;
	}
	
	// 주문 등록
	@PostMapping(value = "/order/insert")
	public int orderInsert(@RequestBody Order order, HttpServletRequest request) {
		int result = 0;
		
		// https://kuzuro.blogspot.com/2018/10/23.html
		
		
		
		
		
		// 장바구니를 생각해서 여러 개 들어오는 짜임으로 변경 필요할 거 같다 => 확장 for문 (42.주문구현3 참조)
		String id = request.getSession().getId();
		order.setId(id);
		result = os.orderInsert(order);
		
		if (result > 0) {
			OrderDetail detail = new OrderDetail();
			// 주문번호가 들어가있는 상태인가????????
			Product product = ps.getProductInfo(order.getProductName());
			
			detail.setOrderNo(order.getOrderNo());
			detail.setProductCode(product.getProductCode());
			detail.setAmount(order.getOrderAmount());
			detail.setPrice(product.getPrice());
			
			int detailResult = os.orderDetailInsert(detail);
			
			if (detailResult > 0) {
				Cart cart = new Cart();
				cart.setId(order.getId());
				cart.setProductCode(product.getProductCode());
				
				cs.orderSuccess(cart);
				
				Product product2 = new Product();
				product2.setProductCode(product.getProductCode());
				product2.setAmountNum(order.getOrderAmount());
				
				ps.stockDown(product2);
			}
		}
		
		return result;
	}
	
}
