package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Cart;

@Mapper
public interface CartMapper {

	List<Cart> cartList(String id);

	int addCart(Cart cart);
	
	int deleteCart(int cartNo);

	int amountModify(Cart cart);

	// orderController에서 요청
	void orderSuccess(Cart cart);

}
