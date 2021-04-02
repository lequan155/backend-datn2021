package com.datn2021.controller;

import java.util.Date;
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

import com.datn2021.model.Invoice;
import com.datn2021.model.OrderFinal;
import com.datn2021.repo.InvoiceRepository;
import com.datn2021.repo.OrderFinalRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/invoice")
public class InvoiceController {
@Autowired private InvoiceRepository repo;
@Autowired private OrderFinalRepository finalRepo;
	
	@GetMapping("")
	public List<Invoice> getListInvoice(){
		return repo.findAll();
	}
	
	@PostMapping("")
	public Invoice createInvoice(@RequestBody Invoice newInvoice){
		newInvoice.setCreateDate(new Date());
		finalRepo.findById(newInvoice.getOderFinal().getId()).get().setDelete(true);
		return repo.save(newInvoice);
	}
	
	@DeleteMapping("/{id}")
	public void deleteInvoice(@PathVariable Long id){
		Invoice invoice = repo.findById(id).get();
		invoice.setDelete(true);
	}

}
