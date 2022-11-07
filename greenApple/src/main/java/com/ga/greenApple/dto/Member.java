package com.ga.greenApple.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("member")
public class Member {
	private String id; // 아이디
	
	private String pw; // 비밀번호
	
	private String name; // 이름
	
	private String tel; // 전화번호
	
	private Date joinDate; // 가입일
	
	private String address; // 주소
	
	private String del; // 탈퇴여부
}
