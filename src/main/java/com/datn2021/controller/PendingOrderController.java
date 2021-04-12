package com.datn2021.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
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

import com.datn2021.dto.OrderItemsDTO;
import com.datn2021.model.Customer;
import com.datn2021.model.Menu;
import com.datn2021.model.OrderFinal;
import com.datn2021.model.OrderItems;
import com.datn2021.model.StoreTable;
import com.datn2021.repo.CustomerRepository;
import com.datn2021.repo.MenuRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;
import com.datn2021.repo.StoreTableRepository;
import com.datn2021.services.OrderItemsService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/table/{id}/pendingorder")
public class PendingOrderController {
@Autowired private OrderItemsRepository repo;
@Autowired private MenuRepository itemRepo;
@Autowired private StoreTableRepository tableRepo;
@Autowired private OrderFinalRepository finalRepo ;
@Autowired private CustomerRepository customerRepo;
@Autowired private OrderItemsService service;

	@GetMapping("")
	public List<OrderItemsDTO> getListPendingOrder(@PathVariable Long id){
		StoreTable table = tableRepo.findById(id).get();
		List<OrderItemsDTO> list = service.findByTableId(id);
//		if(!list.isEmpty()) {
//			tableRepo.findById(id).map(storeTable -> {
//					storeTable.setStatus("Busy");
//					tableRepo.save(storeTable);
//					return list;
//				});
//		}
		return list;
//		if("Busy".equals(table.getStatus())) {
//			List<OrderItemsDTO> list = service.findByTableId(id);
//			return list;
//		}
//		if("OK".equals(table.getStatus())) {
//			table.setStatus("Busy");
//			tableRepo.save(table);
//			return null;
//		}
	}
	
	@PostMapping("/addcustomer")
	public Customer addCustomer(@RequestBody Customer customer, @PathVariable Long id) {
		OrderFinal of = finalRepo.findByTableId(id);
		Customer cus = new Customer();
		Customer newCus = new Customer();
		newCus.setName(customer.getName());
		newCus.setPhoneNo(customer.getPhoneNo());
		
		cus = customerRepo.findByPhoneNo(customer.getPhoneNo());
		if(of==null) {
			of = new OrderFinal();
			of.setStoreTable(tableRepo.findById(id).get());
			finalRepo.save(of);
		}
		if(cus == null) {
				customerRepo.save(newCus);
				of.setCustomer(newCus);
				finalRepo.save(of);
				return newCus;
		}
		of.setCustomer(cus);
		finalRepo.save(of);
		return customerRepo.findByPhoneNo(customer.getPhoneNo());
	}
	
	@PostMapping("")
	public OrderItems createPendingOrder(@RequestBody OrderItems newPendingOrder){
		if(newPendingOrder.getId()==null) {
			newPendingOrder.setId(new Long(0));
		}
		return repo.save(newPendingOrder);
	}
	@PutMapping("/{id}")
	public OrderItems updatePendingOrder(@PathVariable Long id){
		Optional<OrderItems> opt = repo.findById(id);
		OrderItems newPendingOrder = new OrderItems();
		if(opt.isPresent()) {
			newPendingOrder = opt.get();
//			newPendingOrder.setStatus(true);
		}
		return repo.save(newPendingOrder);
	}
	
	@PostMapping("/{id}/addNote")
	public OrderItems updateNotePendingOrder(@RequestBody OrderItems newPendingOrder,@PathVariable Long id) {
		return repo.findById(id).map(
				pendingorder -> {
					pendingorder.setNote(newPendingOrder.getNote());
					return repo.save(pendingorder);
				}).orElseGet(()->{
					newPendingOrder.setId(id);
					return repo.save(newPendingOrder);
				});
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

	@PostMapping("/additem")
	public List<OrderItemsDTO> addMenuItem (@RequestBody List<Map> map,@PathVariable Long id){
		try {
			String itemId = null;
			String itemQty = null;
			OrderFinal newOrderFinal = finalRepo.findByTableId(id);
			for (int i = 0; !map.isEmpty() && i < map.size(); i++) {
				Map item = map.get(i);
				itemId = item.get("id").toString();
				itemQty = item.get("qty").toString();
				if(repo.findByMenuId(Long.parseLong(itemId)) == null){
					OrderItems newOrderItems = new OrderItems();
					newOrderItems.setItem(itemRepo.findMenuItemById(Long.parseLong(itemId)));
					newOrderItems.setOrderFinal(newOrderFinal);
					newOrderItems.setDelete(false);
					newOrderItems.setQty(Integer.parseInt(itemQty));
					newOrderItems.setActive(true);
					repo.save(newOrderItems);
				}
				else {
					OrderItems newOrderItems = repo.findByMenuId(Long.parseLong(itemId));
					int newQty = newOrderItems.getQty() + Integer.parseInt(itemQty);
					newOrderItems.setQty(newQty);
					repo.save(newOrderItems);
				}	
			}
			return service.findByOrderFinalId(newOrderFinal.getId());
		}
		catch (Exception e) {
			
		}
		return service.findByTableId(id); 
	}
	
	@PostMapping("/cancelitem")
	public List<OrderItemsDTO> cancelMenuItem (@RequestBody OrderItems citem,@PathVariable Long id){
		try {
			if(citem!= null) {
				int newQty = citem.getQty() - 1;
				if(newQty <= 0) {
					citem.setActive(false);
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
