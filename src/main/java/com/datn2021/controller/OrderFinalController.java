package com.datn2021.controller;

import java.util.List;

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
import com.datn2021.model.PendingOrder;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.PendingOrderRepository;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/table/{id}/orderfinal")
public class OrderFinalController {
@Autowired private OrderFinalRepository repo;
@Autowired private PendingOrderRepository pendingrepo;	
	@GetMapping("")
	public List<OrderFinal> getListOrderFinal(@PathVariable Long id){
//		List<PendingOrder> listPendingOrder = pendingrepo.findAll();
		List<PendingOrder> listPendingOrder = pendingrepo.findOrderTableById(id);
		OrderFinal finalItem = new OrderFinal();
		if(!listPendingOrder.isEmpty()) {
			for(PendingOrder item : listPendingOrder) {
				finalItem.setTableId(id);
				finalItem.setItemId(item.getItemId());
				finalItem.setItemName(item.getItemName());
				finalItem.setPrice(item.getPrice());
				finalItem.setCustomerID(null);
				repo.save(finalItem);
			}
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
					OrderFinal.setItemId(newOrderFinal.getItemId());
					OrderFinal.setTableId(newOrderFinal.getTableId());
					OrderFinal.setCustomerID(newOrderFinal.getCustomerID());
					OrderFinal.setItemName(newOrderFinal.getItemName());
					OrderFinal.setPrice(newOrderFinal.getPrice());
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
