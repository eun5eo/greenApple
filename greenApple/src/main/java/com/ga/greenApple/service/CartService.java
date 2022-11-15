package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Cart;

public interface CartService {

	// 카트 리스트
	List<Cart> cartList(String id);
	
	// 구매하려는 상품 정보 결제창에 보여주기
	List<Cart> orderWish(String id);

	// 카트 담기
	int addCart(Cart cart);
	
	// 카트 수정
	int update(Cart cart);
	
	// 카트 삭제
	int deleteCart(int cartNo);

}
