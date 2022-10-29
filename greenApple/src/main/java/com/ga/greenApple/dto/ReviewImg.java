package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("reviewImg")
public class ReviewImg {
	private int reviewImgNo; // 사진 번호
	private String id; // 아이디 fk
	private int productCode; // 상품 코드 fk
	private String fileName; // 파일명
}
