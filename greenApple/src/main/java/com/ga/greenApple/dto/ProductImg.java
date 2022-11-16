package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("productImg")
public class ProductImg {
	private int productImgNo; // 사진 번호
	
	private int productCode;  // 상품 코드 fk
	
	private String fileName;  // 파일명
}
