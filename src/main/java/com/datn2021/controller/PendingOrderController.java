package com.datn2021.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.model.Menu;
import com.datn2021.model.PendingOrder;
import com.datn2021.repo.MenuRepository;
import com.datn2021.repo.PendingOrderRepository;

@RestController
@RequestMapping("/table/{id}/pendingorder")
public class PendingOrderController {
@Autowired private PendingOrderRepository repo;
@Autowired private MenuRepository itemRepo;
	
	@GetMapping("")
	public List<PendingOrder> getListPendingOrder(@PathVariable Long id){
		return repo.findAll(id);
	}
	
	@PostMapping("")
	public PendingOrder createPendingOrder(@RequestBody PendingOrder newPendingOrder){
		newPendingOrder.toString();
		return repo.save(newPendingOrder);
	
	}@PutMapping("/{id}")
	public PendingOrder updatePendingOrder(@RequestBody PendingOrder newPendingOrder, @PathVariable Long id){
		return repo.findById(id).map(
				PendingOrder -> {
					PendingOrder.setId(newPendingOrder.getId());
					PendingOrder.setItemId(newPendingOrder.getItemId());
					PendingOrder.setTableId(newPendingOrder.getTableId());
					PendingOrder.setPrice(newPendingOrder.getPrice());
					PendingOrder.setPicture(newPendingOrder.getPicture());
					PendingOrder.setStatus(newPendingOrder.isStatus());
					
					return repo.save(PendingOrder);
				}).orElseGet(()->{
					newPendingOrder.setId(id);
					return repo.save(newPendingOrder);
				});
	}
	@DeleteMapping("/{id}")
	public void deletePendingOrder(@PathVariable Long id){
		repo.deleteById(id);
	}

	@GetMapping("/{param}")
	public List<PendingOrder> addMenuItem (@RequestBody List<Long> list,@PathVariable Long id, @PathVariable String param){
		try {
			if("addItem".equals(param)) {
				for(Long i:list) {
					Menu item = new Menu();
					item = itemRepo.findMenuItemById(i);
					PendingOrder newPendingOrder = new PendingOrder();
					newPendingOrder.setItemId(item.getId());
					newPendingOrder.setPicture(item.getPicture());
					newPendingOrder.setPrice(item.getPrice());
					newPendingOrder.setTableId(id);
					newPendingOrder.setItemName(item.getName());
					repo.save(newPendingOrder);
				}
			}
			else {
				return repo.findAll(id);
			}
			return repo.findAll(id);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return repo.findAll(id); 
	}
}
