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
	
	// 구매하려는 상품 정보 결제창에 보여주기
	@Override
	public List<Cart> orderWish(String id) {
		return cm.orderWish(id);
	}

	// 카트 담기
	@Override
	public int addCart(Cart cart) {
		return cm.addCart(cart);
	}
	
	// 카트 수정
	@Override
	public int cartUpdate(Cart cart) {
		return cm.cartUpdate(cart);
	}

	// 카트 삭제
	@Override
	public int deleteCart(int cartNo) {
		return cm.deleteCart(cartNo);
	}

}
