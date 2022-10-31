package com.ga.greenApple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.service.CartService;

@RestController
public class CartController {
	@Autowired
	private CartService cs;
	
	// 블로그 읽고 참조해서 작성하기
}
