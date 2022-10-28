package com.ga.greenApple.dto;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Alias("review")
public class Review {
	private int reviewNo;
	private String id;
	private int productCode;
	private String content;
	private String fileName;
	
	// upload용
	private MultipartFile file;
}
