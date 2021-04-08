package com.datn2021.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.dto.CustomerDTO;
import com.datn2021.dto.OrderItemsDTO;
import com.datn2021.model.Customer;
import com.datn2021.model.OrderFinal;
import com.datn2021.model.OrderItems;
import com.datn2021.repo.CustomerRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;
import com.datn2021.services.OrderItemsService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/table/{id}/orderfinal")
public class OrderFinalController {
@Autowired private OrderFinalRepository repo;
@Autowired private OrderItemsRepository pendingrepo;
@Autowired private OrderItemsService itemService;
@Autowired private CustomerRepository customerRepo;
	@GetMapping("/{id}")
	public List<OrderItemsDTO> getListOrderFinal(@PathVariable Long id){
		OrderFinal orderFinal = repo.findById(id).get();
		List<OrderItemsDTO> listFinalItems = itemService.findByOrderFinalId(id);
		return listFinalItems;
	}
	
	@GetMapping("/findCustomer/{id}")
	public Customer getCustomerByFinalId(@PathVariable Long id) {
		Customer cus = customerRepo.findCustomerByOrderFinal(id);
		return cus;
	}
	
	@PostMapping("")
	public OrderFinal createOrderFinal(@RequestBody OrderFinal newOrderFinal){
		if(newOrderFinal.getId()==null) {
			newOrderFinal.setId(new Long(0));
		}
		return repo.save(newOrderFinal);
	
	}@PutMapping("/{id}")
	public OrderFinal updateOrderFinal(@RequestBody OrderFinal newOrderFinal, @PathVariable Long id){
		return repo.findById(id).map(
				OrderFinal -> {
					OrderFinal.setId(newOrderFinal.getId());
					return repo.save(OrderFinal);
				}).orElseGet(()->{
					newOrderFinal.setId(id);
					return repo.save(newOrderFinal);
				});
	}
	@DeleteMapping("/{id}")
	public void deleteOrderFinal(@PathVariable Long id){
		Optional<OrderFinal> opt = repo.findById(id);
		OrderFinal newOrderFinal = new OrderFinal();
		if(opt.isPresent()) {
			newOrderFinal = opt.get();
			newOrderFinal.setDelete(true);
		}
		repo.save(newOrderFinal);
	}

}
