package com.datn2021.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.model.OrderFinal;
import com.datn2021.model.OrderItems;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/table/{id}/orderfinal")
public class OrderFinalController {
@Autowired private OrderFinalRepository repo;
@Autowired private OrderItemsRepository pendingrepo;	
	@GetMapping("")
	public List<OrderFinal> getListOrderFinal(@PathVariable Long id){
		List<OrderItems> listPendingOrder = pendingrepo.findAll();
		if(!listPendingOrder.isEmpty()) {
			
		}
		return repo.findAll();
	}
	
	@PostMapping("")
	public OrderFinal createOrderFinal(@RequestBody OrderFinal newOrderFinal){
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
