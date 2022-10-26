package com.ga.greenApple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.service.MemberService;

@RestController
public class MemberController {
	@Autowired
	private MemberService ms;
}
