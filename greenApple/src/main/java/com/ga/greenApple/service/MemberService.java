package com.ga.greenApple.service;

import com.ga.greenApple.dto.Member;

public interface MemberService {

	Member select(String id);

	int insert(Member member);

	int update(Member member);

	int delete(String id);

}
