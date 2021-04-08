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

import com.datn2021.model.Customer;
import com.datn2021.repo.CustomerRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/customer")
public class CustomerController {
	@Autowired CustomerRepository repo;
	
	@GetMapping("")
	public List<Customer> getListCustomer(){
		return repo.findAll();
	}
	
	@PostMapping("")
	public Customer createCustomer(@RequestBody Customer newCustomer){
		if(newCustomer.getId()==null) {
			newCustomer.setId(new Long(0));
		}
		return repo.save(newCustomer);
	
	}
	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable Long id) {
		return repo.findById(id).get();
	}
	
	@PutMapping("/{id}")
	public Customer updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id){
		return repo.findById(id).map(
				Customer -> {
					Customer.setName(newCustomer.getName());
					Customer.setPhoneNo(newCustomer.getPhoneNo());
					Customer.setAddress(newCustomer.getAddress());
					Customer.setEmail(newCustomer.getEmail());
					return repo.save(Customer);
				}).orElseGet(()->{
					newCustomer.setId(id);
					return repo.save(newCustomer);
				});
	}
	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable Long id){
		Customer Customer = repo.findById(id).get();
		Customer.setDelete(true);
	}

}
