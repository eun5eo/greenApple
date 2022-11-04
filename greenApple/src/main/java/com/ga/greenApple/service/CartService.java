package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Cart;

public interface CartService {

	List<Cart> cartList(String id);

	int addCart(Cart cart);
	
	int deleteCart(int cartNo);

	int amountModify(Cart cart);

	// orderController에서 요청
	void orderSuccess(Cart cart);

}
