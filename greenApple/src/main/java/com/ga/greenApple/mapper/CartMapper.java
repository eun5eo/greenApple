package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Cart;

@Mapper
public interface CartMapper {

	// 카트 리스트
	List<Cart> cartList(String id);

	// 카트 담기
	int addCart(Cart cart);
	
	// 카트 삭제
	int deleteCart(int cartNo);

	// 카트 수량 수정
	int amountModify(Cart cart);

}
