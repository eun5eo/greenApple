package com.ga.greenApple.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.service.MemberService;

@RestController
public class MemberController {
	@Autowired
	private MemberService ms;
	
	@Autowired
	private BCryptPasswordEncoder bpe;
	
	// id 중복 체크
	@RequestMapping(value = "/join/idCheck")
	public int idCheck(@RequestParam String id) {
		int result = 0;
		Member member = ms.select(id);
		
		if (member == null)
			result = 1;
		else 
			result = -1;
		
		return result;
	}
	
	// 회원가입
	@PostMapping(value = "/join")
	public int join(@RequestBody Member member) {
		int result = 0;
		
		// 한 번 더 아이디 중복 체크
		Member memberCheck = ms.select(member.getId());
		
		if (memberCheck == null) {
			String encPass = bpe.encode(member.getPw()); // 암호화
			member.setPw(encPass);
			
			result = ms.insert(member);
		} else result = -1; // 이미 존재하는 경우
		
		return result;
	}
	
	// 로그인
	@PostMapping(value = "/login")
	public int login(@RequestBody Member member, HttpSession session) {
		int result = 0;
		
		// 입력 받은 id가 있는지 확인을 위한 것
		Member memberCheck = ms.select(member.getId());
		
		if (memberCheck == null || memberCheck.getDel().equals("y")) 
			result = -1; // 존재하지 않는 id
		else if (bpe.matches(member.getPw(), memberCheck.getPw())) {
			result = 1; // id와 pw 모두 일치할 경우
			
			session.setAttribute("id", member.getId());
		}
		
		return result;
	}
	
	// 로그아웃
	@GetMapping(value = "/logout")
	public int logout(HttpSession session) {
		session.invalidate(); // 세션 지우기
		
		return 1;
	}
	
	// 회원 정보
	@RequestMapping(value = "/member/information")
	public Member information(@RequestParam String id, HttpSession session) {
		Member member = ms.select(id);
		
		return member;
	}
	
	// 회원정보 업데이트
	@PostMapping(value = "/member/update")
	public int update(@RequestBody Member member, HttpSession session) {
		int result = 0;
		
		String encPass = bpe.encode(member.getPw());
		member.setPw(encPass);
		
		result = ms.update(member);
		
		return result;
	}
	
	// 회원 탈퇴
	@PostMapping(value = "/member/delete")
	public int delete(@RequestBody Member member, HttpSession session) {
		int result = 0;
		
		String id = (String) session.getAttribute("id");
		Member memberChk = ms.select(id);
		
		// 받은 비밀번호가 현재 비밀번호와 일치한지 비교
		if (bpe.matches(member.getPw(), memberChk.getPw())) 
			result = ms.delete(id);
		else if (!bpe.matches(member.getPw(), memberChk.getPw()))
			result = -1; // 비밀번호 다름
		
		if (result > 0) session.invalidate();
		
		return result;
	}
}
