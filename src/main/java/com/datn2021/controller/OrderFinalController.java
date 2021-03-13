package com.datn2021.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.model.OrderFinal;
import com.datn2021.repo.OrderFinalRepository;

@RestController
@RequestMapping("/orderfinal")
public class OrderFinalController {
@Autowired private OrderFinalRepository repo;
	
	@GetMapping("")
	public List<OrderFinal> getListOrderFinal(){
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
					OrderFinal.setItemId(newOrderFinal.getItemId());
					OrderFinal.setOrderID(newOrderFinal.getOrderID());
					OrderFinal.setTableId(newOrderFinal.getTableId());
					OrderFinal.setTotal(newOrderFinal.getTotal());
					return repo.save(OrderFinal);
				}).orElseGet(()->{
					newOrderFinal.setId(id);
					return repo.save(newOrderFinal);
				});
	}
	@DeleteMapping("/{id}")
	public void deleteOrderFinal(@PathVariable Long id){
		repo.deleteById(id);;
	}

}
