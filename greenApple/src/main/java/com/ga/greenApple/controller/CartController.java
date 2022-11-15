package com.ga.greenApple.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Cart;
import com.ga.greenApple.service.CartService;

@RestController
public class CartController {
	@Autowired
	private CartService cs;
	
	// 카트 리스트
	@RequestMapping(value = "/cart")
	public List<Cart> cartList(HttpSession session) {
		String id = (String) session.getAttribute("id");
		List<Cart> cartList = cs.cartList(id);
		
		return cartList;
	}
	
	// 구매하려는 상품 정보 결제창에 보여주기
	@RequestMapping(value = "/cart/orderWish")
	public List<Cart> orderWish(HttpSession session) {
		String id = (String) session.getAttribute("id");
		
		List<Cart> orderWish = cs.orderWish(id);
		
		return orderWish;
	}
	
	// 카트 담기
	@PostMapping(value = "/cart/add")
	public int cartAdd(@RequestBody Cart cart, HttpSession session) {
		int result = cs.addCart(cart);
		
		return result;
	}
	
	// 카트 수정
	@PostMapping(value = "/cart/update")
	public int update(@RequestBody Cart cart, HttpSession session) {
		int result = cs.update(cart);
		
		return result;
	}
	
	// 카트 삭제
	@GetMapping(value = "/cart/delete")
	public int cartDelete(@RequestParam int cartNo, HttpSession session) {
		int result = cs.deleteCart(cartNo);
		
		return result;
	}
	
}
