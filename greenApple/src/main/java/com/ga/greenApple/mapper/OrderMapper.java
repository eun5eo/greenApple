package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Order;

@Mapper
public interface OrderMapper {

	List<Order> memberOrderList(String id);

}
