package com.ga.greenApple.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Cart;
import com.ga.greenApple.dto.Member;
import com.ga.greenApple.service.CartService;

@RestController
public class CartController {
	@Autowired
	private CartService cs;
	
	// 카트 리스트
	@PostMapping(value = "/cart")
	public List<Cart> cartList(HttpServletRequest request) {
		String id = request.getSession().getId();
		List<Cart> cartList = cs.cartList(id);
		
		return cartList;
	}
	
	// 카트 담기
	@PostMapping(value = "/cart/add")
	public int cartAdd(@RequestBody Cart cart, HttpSession session) {
		int result = cs.addCart(cart);
		
		return result;
	}
	
	// 카트 삭제
	@PostMapping(value = "/cart/delete")
	public int cartDelete(@RequestBody int cartNo, HttpSession session) {
		int result = cs.deleteCart(cartNo);
		
		return result;
	}
	
	// 카트 수량 수정
	@PostMapping(value = "/cart/modify")
	public int amountModify(@RequestBody Cart cart, HttpSession session) {
		int result = cs.amountModify(cart);
		
		return result;
	}

}
