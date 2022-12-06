package com.ga.greenApple.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("forAdmin")
public class AdminData {
	private String tag; // 검색 태그
	
	private String keyword; // 검색 내용
	
	private String pageNum; // 페이지 번호
	
	private int currentPage; // 현재 페이지
	
	private int startRow; // 페이징 시작 번호
	
	private int endRow; // 페이징 끝 번호
	
	private int startPage; // 페이징용 시작 번호
	
	private int endPage; // 페이징용 끝 번호
	
	private int totalPage; // 총 페이지수
	
	private List<Product> productList; // 상품 리스트
	
	private List<Member> memberList; // 멤버 리스트
	
	private List<Review> reviewList; // 리뷰 리스트
}
