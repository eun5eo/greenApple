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

	@Override
	public List<Cart> cartList(String id) {
		return cm.cartList(id);
	}

	@Override
	public int addCart(Cart cart) {
		return cm.addCart(cart);
	}

	@Override
	public int deleteCart(int cartNo) {
		return cm.deleteCart(cartNo);
	}
	
	@Override
	public int amountModify(Cart cart) {
		return cm.amountModify(cart);
	}

	// orderController에서 요청
	@Override
	public void orderSuccess(Cart cart) {
		cm.orderSuccess(cart);
		
	}

}
