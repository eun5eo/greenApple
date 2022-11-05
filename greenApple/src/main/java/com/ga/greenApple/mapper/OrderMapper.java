package com.ga.greenApple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ga.greenApple.dto.Order;
import com.ga.greenApple.dto.OrderDetail;

@Mapper
public interface OrderMapper {

	List<Order> memberOrderList(String id);

	int orderInsert(Order order);

	int orderDetailInsert(OrderDetail detail);

}
