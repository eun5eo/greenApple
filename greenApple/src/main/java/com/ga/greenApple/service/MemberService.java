package com.ga.greenApple.service;

import java.util.List;

import com.ga.greenApple.dto.Member;
import com.ga.greenApple.dto.Review;

public interface MemberService {

	// id로 멤버 불러오기
	Member select(String id);

	// 회원가입
	int insert(Member member);

	// 회원정보 업데이트
	int update(Member member);

	// 회원 탈퇴
	int delete(String id);

	// 회원별 작성한 리뷰 보기
	List<Review> myReview(String id);

}
