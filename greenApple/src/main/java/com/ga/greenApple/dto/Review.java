package com.ga.greenApple.dto;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Alias("review")
public class Review {
	private String reviewId; // 리뷰 아이디
	
	private String id; // 아이디
	
	private String productCode; // 상품 코드
	
	private String content; // 리뷰 내용
	
	private String fileName; // 파일명
	
	private Date reviewDate; // 작성일
	
	private String del; // 삭제 여부
	
	// upload용
	private List<MultipartFile> files;
}
