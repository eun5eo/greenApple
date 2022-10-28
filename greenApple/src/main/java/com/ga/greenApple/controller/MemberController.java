package com.ga.greenApple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.service.MemberService;

@RestController
public class MemberController {
	@Autowired
	private MemberService ms;
	
	// 회원가입
	@RequestMapping("/join")
	public String join(Member member) {
		
		return "";
	}
}
