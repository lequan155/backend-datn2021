package com.datn2021.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.model.User;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
	@Autowired private UserRepository repo;
	
	@PostMapping("/getUser")
	public User getUser(@RequestBody Map<String, String> userdata){
//		if(dataInvoice != null && !dataInvoice.isEmpty()) {
//			Date fromDate = dataInvoice.get("fromDate");
//			Date toDate = dataInvoice.get("toDate");
//			count = repo.countInvoiceByYearToYear(fromDate, toDate);
//		}
		String username = userdata.get("username");
		String password = userdata.get("password");
		return repo.findByUserName(username, password);
	}
}
