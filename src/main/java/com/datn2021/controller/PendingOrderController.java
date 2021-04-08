package com.datn2021.controller;

import java.util.List;
import java.util.Map;
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

	@PostMapping("/{param}")
	public List<OrderItemsDTO> addMenuItem (@RequestBody List<Long> list,@PathVariable Long id, @PathVariable String param){
		try {
			OrderFinal newOrderFinal = finalRepo.findByTableId(id);
			if("addItem".equals(param)) {
				for(Long i:list) {
					Menu item = new Menu();
					OrderItems newOrderItems = new OrderItems();
					item = itemRepo.findMenuItemById(i);
					newOrderItems.setItem(item);
					newOrderItems.setOrderFinal(newOrderFinal);
					newOrderItems.setDelete(false);
					//newOrderItems.setStatus(false);
					newOrderItems.setQty(1);
					//newPendingOrder.setNote(null);
					newOrderItems.setStatus(false);
					repo.save(newOrderItems);
				}	
			}
			else {
				return service.findByOrderFinalId(newOrderFinal.getId());
			}
			return service.findByOrderFinalId(newOrderFinal.getId());
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return service.findByTableId(id); 
	}
}
