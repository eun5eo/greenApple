package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("forAdmin")
public class AdminData {
	private String tag; // 검색 태그
	
	private String keyword; // 검색 내용
	
	private String pageNum; // 페이징용
	
	private int startRow; // 페이징용 시작 번호
	
	private int endRow; // 페이징용 끝 번호
}
