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
	
	private int startRow; // 
	
	private int endRow;
	
	private int startPage; // 페이징용 시작 번호
	
	private int endPage; // 페이징용 끝 번호
	
	private List<Member> memberList;
	
	private List<Product> productList;
	
	private List<Review> reviewList;
}
