package com.datn2021.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.datn2021.dto.CustomerDTO;
import com.datn2021.dto.OrderFinalDTO;
import com.datn2021.model.Customer;
import com.datn2021.model.OrderFinal;
import com.datn2021.repo.CustomerRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;

@Service
public class OrderFinalService {
	@Autowired private OrderFinalRepository repo;
	@Autowired private OrderItemsRepository pendingrepo;
	@Autowired private CustomerRepository customerRepo;
	@Autowired private ModelMapper mapper;

	public OrderFinalDTO getOrderFinalByTableId(@PathVariable Long id) {
		OrderFinal orderFinal = repo.findByTableId(id);
		OrderFinalDTO orderFinalDTO = new OrderFinalDTO();
		if(null != orderFinal) {
			orderFinalDTO = mapper.map(orderFinal, OrderFinalDTO.class);
		}
		return orderFinalDTO;
	}
	
	public CustomerDTO getCustomerByFinalId(@PathVariable Long id) {
		if(id!=null) {
			Customer cus = customerRepo.findCustomerByOrderFinal(id);
			CustomerDTO cusdto = mapper.map(cus, CustomerDTO.class);
			return cusdto;
		}
		return null;
	}

	public CustomerDTO getCustomerByTableId(@PathVariable Long id) {
		Customer cus = customerRepo.findCustomerByTableId(id);
		CustomerDTO cusdto = mapper.map(cus, CustomerDTO.class);
		return cusdto;
	}
	
	
}
