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

import com.datn2021.dto.OrderItemsDTO;
import com.datn2021.model.Menu;
import com.datn2021.model.OrderFinal;
import com.datn2021.model.OrderItems;
import com.datn2021.model.StoreTable;
import com.datn2021.repo.MenuRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;
import com.datn2021.repo.StoreTableRepository;
import com.datn2021.services.OrderFinalService;
import com.datn2021.services.OrderItemsService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/table/{id}/pendingorder")
public class PendingOrderController {
@Autowired private OrderItemsRepository repo;
@Autowired private MenuRepository itemRepo;
@Autowired private StoreTableRepository tableRepo;
@Autowired private OrderFinalRepository finalRepo ;
@Autowired private OrderItemsService service;

	@GetMapping("")
	public List<OrderItemsDTO> getListPendingOrder(@PathVariable Long id){
		List<OrderItemsDTO> list = service.findByTableId(id);
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
	public OrderItems createPendingOrder(@RequestBody OrderItems newPendingOrder){
		newPendingOrder.toString();
		return repo.save(newPendingOrder);
	}
	@PutMapping("/{id}")
	public OrderItems updatePendingOrder(@PathVariable Long id){
		Optional<OrderItems> opt = repo.findById(id);
		OrderItems newPendingOrder = new OrderItems();
		if(opt.isPresent()) {
			newPendingOrder = opt.get();
			newPendingOrder.setStatus(true);
		}
		return repo.save(newPendingOrder);
	}
	
	@DeleteMapping("/{id}")
	public void deletePendingOrder(@PathVariable Long id){
		Optional<OrderItems> opt = repo.findById(id);
		OrderItems newPendingOrder = new OrderItems();
		if(opt.isPresent()) {
			newPendingOrder = opt.get();
			newPendingOrder.setDelete(true);
		}
		repo.save(newPendingOrder);
	}

	@PostMapping("/{param}")
	public List<OrderItemsDTO> addMenuItem (@RequestBody List<Long> list,@PathVariable Long id, @PathVariable String param){
		try {
			if("addItem".equals(param)) {
				OrderFinal newOrderFinal = new OrderFinal();
				if(repo.findByTableId(id).isEmpty()) {
					newOrderFinal.setStoreTable(tableRepo.findById(id).get());
					finalRepo.save(newOrderFinal);
				}
				else {
					newOrderFinal = finalRepo.findById(repo.findFinalOrderId(id)).get();
				}
				for(Long i:list) {
					Menu item = new Menu();
					OrderItems newOrderItems = new OrderItems();
					item = itemRepo.findMenuItemById(i);
					newOrderItems.setItem(item);
					newOrderItems.setOrderFinal(newOrderFinal);
					newOrderItems.setDelete(false);
					newOrderItems.setStatus(false);
					//newPendingOrder.setQty(0);
					//newPendingOrder.setNote(null);
					newOrderItems.setStatus(false);
					repo.save(newOrderItems);
				}	
			}
			else {
				return service.findByTableId(id);
			}
			return service.findByTableId(id);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return service.findByTableId(id); 
	}
}
