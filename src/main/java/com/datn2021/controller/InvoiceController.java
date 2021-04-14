package com.datn2021.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.datn2021.model.Menu;
import com.datn2021.model.OrderFinal;
import com.datn2021.repo.CustomerRepository;
import com.datn2021.repo.InvoiceRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.SalesRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/invoice")
public class InvoiceController {
@Autowired private InvoiceRepository repo;
@Autowired private OrderFinalRepository finalRepo;
@Autowired private CustomerRepository cusRepo;
@Autowired private SalesRepository saleRepo;
	
	@GetMapping("")
	public List<Invoice> getListInvoice(){
		return repo.findAll();
	}
	
	@PostMapping("")
	public ResponseEntity<Invoice> createInvoice(@RequestBody Map<String, String> dataInvoice){
		Invoice newInvoice = new Invoice();
		newInvoice.setTotal(BigDecimal.valueOf(Double.parseDouble(dataInvoice.get("total"))));
		newInvoice.setOderFinal(finalRepo.findById(Long.parseLong(dataInvoice.get("order_final_id"))).get());
		newInvoice.setCustomer(cusRepo.findById(Long.parseLong(dataInvoice.get("customer_id"))).get());
		if(null != dataInvoice.get("sale_id")) {
			newInvoice.setSale(saleRepo.findById(Long.parseLong(dataInvoice.get("sale_id"))).get());
		}
		newInvoice.setCreateDate(new Date());
		
		OrderFinal of = finalRepo.findById(newInvoice.getOderFinal().getId()).get();
		of.setDelete(true);
		finalRepo.save(of);
		
		return new ResponseEntity<>(repo.save(newInvoice),HttpStatus.OK);
	}
	
	@PostMapping("/totalbydate")
	public ResponseEntity<BigDecimal> totalInvoiceByDate(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = null;
		if(!dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			total = repo.findTotalByDate(fromDate, toDate);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/listbydate")
	public ResponseEntity<List<Invoice>> ListInvoiceByDate(@RequestBody Map<String, Date> dataInvoice){
		List<Invoice> list = new ArrayList<>();
		if(!dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			list = repo.findListInvoiceByDateToDate(fromDate, toDate);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbyonedate")
	public ResponseEntity<List<Invoice>> ListInvoiceByOneDate(@RequestBody Date dataInvoice){
		List<Invoice> list = new ArrayList<>();
		if(dataInvoice != null) {
			list = repo.findListInvoiceByOneDate(dataInvoice);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/bestseller")
	public ResponseEntity<Menu> getBestSellMenu(){
		Menu menu = repo.getBestSeller();
		return new ResponseEntity<Menu>(menu,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public void deleteInvoice(@PathVariable Long id){
		Invoice invoice = repo.findById(id).get();
		invoice.setDelete(true);
	}

}
