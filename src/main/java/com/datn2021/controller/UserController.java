package com.datn2021.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.model.Sales;
import com.datn2021.model.User;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
	@Autowired private UserRepository repo;
	
	@GetMapping("")
	public List<User> getListUser() {
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable Long id){
		return repo.findById(id);
	}
	
	@PostMapping("")
	public User createUser(@RequestBody User newUser){
		if(newUser.getId()==null) {
			newUser.setId(new Long(0));
		}
		return repo.save(newUser);
	
	}
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User newUser, @PathVariable Long id){
		return repo.findById(id).map(
				User -> {
//					User.setId(newSales.getId());
					User.setFullName(newUser.getFullName());
					User.setUserName(newUser.getUserName());
					User.setPassWord(newUser.getPassWord());
					User.setPhoneNo(newUser.getPhoneNo());
					User.setActive(newUser.isActive());
					User.setEmail(newUser.getEmail());
					User.setRole(newUser.getRole());
					return repo.save(User);
				}).orElseGet(()->{
					newUser.setId(id);
					return repo.save(newUser);
				});
	}
	
	@PostMapping("/getUser")
	public User getUser(@RequestBody Map<String, String> userdata){
//		if(dataInvoice != null && !dataInvoice.isEmpty()) {
//			Date fromDate = dataInvoice.get("fromDate");
//			Date toDate = dataInvoice.get("toDate");
//			count = repo.countInvoiceByYearToYear(fromDate, toDate);
//		}
		String username = userdata.get("username");
		String password = userdata.get("password");
		return repo.findByUserNameAndPassWord(username, password);
	}
}
