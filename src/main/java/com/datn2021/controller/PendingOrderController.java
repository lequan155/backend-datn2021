package com.datn2021.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.datn2021.model.StoreTable;
import com.datn2021.repo.MenuRepository;
import com.datn2021.repo.PendingOrderRepository;
import com.datn2021.repo.StoreTableRepository;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/table/{id}/pendingorder")
public class PendingOrderController {
@Autowired private PendingOrderRepository repo;
@Autowired private MenuRepository itemRepo;
@Autowired private StoreTableRepository tableRepo;	
	
	@GetMapping("")
	public List<PendingOrder> getListPendingOrder(@PathVariable Long id){
		List<PendingOrder> list = repo.findAll(id);
		if(!list.isEmpty()) {
			tableRepo.findById(id).map(storeTable -> {
					storeTable.setStatus("Busy");
					tableRepo.save(storeTable);
					return list;
				});
		}
		return list;
	}
	
	@PostMapping("")
	public PendingOrder createPendingOrder(@RequestBody PendingOrder newPendingOrder){
		newPendingOrder.toString();
		return repo.save(newPendingOrder);
	}
	@PutMapping("/{id}")
	public PendingOrder updatePendingOrder(@PathVariable Long id){
		Optional<PendingOrder> opt = repo.findById(id);
		PendingOrder newPendingOrder = new PendingOrder();
		if(opt.isPresent()) {
			newPendingOrder = opt.get();
			newPendingOrder.setStatus(true);
		}
		return repo.save(newPendingOrder);
	}
	
	@DeleteMapping("/{id}")
	public void deletePendingOrder(@PathVariable Long id){
		repo.deleteById(id);
	}

	@PostMapping("/{param}")
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
