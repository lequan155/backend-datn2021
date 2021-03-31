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

import com.datn2021.model.Invoice;
import com.datn2021.repo.InvoiceRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/invoice")
public class InvoiceController {
@Autowired private InvoiceRepository repo;
	
	@GetMapping("")
	public List<Invoice> getListInvoice(){
		return repo.findAll();
	}
	
	@PostMapping("")
	public Invoice createInvoice(@RequestBody Invoice newInvoice){
		return repo.save(newInvoice);
	
	}
//	@PutMapping("/{id}")
//	public Invoice updateInvoice(@RequestBody Invoice newInvoice, @PathVariable Long id){
//		return repo.findById(id).map(
//				Invoice -> {
//					Invoice.setBranchId(null);
//					Invoice.setDate(null);
//					Invoice.setOderFinalId(id);
//					Invoice.setSaleId(null);
//					Invoice.setTotal(id);
//					return repo.save(Invoice);
//				}).orElseGet(()->{
//					newInvoice.setId(id);
//					return repo.save(newInvoice);
//				});
//	}
	@DeleteMapping("/{id}")
	public void deleteInvoice(@PathVariable Long id){
		repo.deleteById(id);;
	}

}
