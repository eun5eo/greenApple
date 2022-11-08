package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Cart;
import com.ga.greenApple.mapper.CartMapper;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartMapper cm;

	// 카트 리스트
	@Override
	public List<Cart> cartList(String id) {
		return cm.cartList(id);
	}

	// 카트 담기
	@Override
	public int addCart(Cart cart) {
		return cm.addCart(cart);
	}

	// 카트 삭제
	@Override
	public int deleteCart(int cartNo) {
		return cm.deleteCart(cartNo);
	}
	
	// 카트 수량 수정
	@Override
	public int amountModify(Cart cart) {
		return cm.amountModify(cart);
	}

}
