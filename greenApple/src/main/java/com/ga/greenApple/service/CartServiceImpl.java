package com.ga.greenApple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.mapper.CartMapper;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartMapper cm;
}
