package com.ga.greenApple.service;

import com.ga.greenApple.dto.Member;

public interface MemberService {

	// id로 멤버 불러오기
	Member select(String id);

	// 회원가입
	int insert(Member member);

	// 회원정보 업데이트
	int update(Member member);

	// 회원 탈퇴
	int delete(String id);

}
