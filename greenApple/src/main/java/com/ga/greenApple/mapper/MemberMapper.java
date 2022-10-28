package com.ga.greenApple.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Member;

@Mapper
public interface MemberMapper {

	Member select(String id);

	int insert(Member member);

	int update(Member member);

	int delete(String id);

}
