package com.datn2021.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn2021.dto.OrderItemsDTO;
import com.datn2021.model.OrderItems;
import com.datn2021.repo.OrderItemsRepository;

@Service
public class OrderItemsService {
	@Autowired private ModelMapper mapper;
	@Autowired private OrderItemsRepository repo;
	
	public List<OrderItemsDTO> findByTableId(Long id) {
		List<OrderItems> list = repo.findByTableId(id);
		List<OrderItemsDTO> listDTO = new ArrayList<OrderItemsDTO>();
		for (OrderItems orderItems : list) {
			OrderItemsDTO orderItemsDTO = mapper.map(orderItems, OrderItemsDTO.class);
			listDTO.add(orderItemsDTO);
		}
		return listDTO;
	}
}
