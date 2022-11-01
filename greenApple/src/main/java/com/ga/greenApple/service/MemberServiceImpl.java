package com.ga.greenApple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper mm;

	@Override
	public Member select(String id) {
		return mm.select(id);
	}

	@Override
	public int insert(Member member) {
		return mm.insert(member);
	}

	@Override
	public int update(Member member) {
		return mm.update(member);
	}

	@Override
	public int delete(String id) {
		return mm.delete(id);
	}

}
