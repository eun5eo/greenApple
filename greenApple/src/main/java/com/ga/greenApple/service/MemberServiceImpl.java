package com.ga.greenApple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Review;
import com.ga.greenApple.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper mm;

	// id로 멤버 불러오기
	@Override
	public Member select(String id) {
		return mm.select(id);
	}

	// 회원가입
	@Override
	public int insert(Member member) {
		return mm.insert(member);
	}

	// 회원정보 업데이트
	@Override
	public int update(Member member) {
		return mm.update(member);
	}

	// 회원 탈퇴
	@Override
	public int delete(String id) {
		return mm.delete(id);
	}

	// 회원별 작성한 리뷰 보기
	@Override
	public List<Review> myReview(String id) {
		return mm.myReview(id);
	}

}
