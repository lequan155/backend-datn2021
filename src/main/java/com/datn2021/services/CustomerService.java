package com.datn2021.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn2021.dto.CustomerDTO;
import com.datn2021.model.Customer;
import com.datn2021.repo.CustomerRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;

@Service
public class CustomerService {
	@Autowired private ModelMapper mapper;
	@Autowired private CustomerRepository repo;
	@Autowired private OrderFinalRepository finalRepo;
	
	
}
