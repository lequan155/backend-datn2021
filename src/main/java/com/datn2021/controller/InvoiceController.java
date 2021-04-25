package com.datn2021.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.model.Invoice;
import com.datn2021.model.Menu;
import com.datn2021.model.OrderFinal;
import com.datn2021.model.OrderItems;
import com.datn2021.model.Sales;
import com.datn2021.model.StoreTable;
import com.datn2021.repo.CustomerRepository;
import com.datn2021.repo.InvoiceRepository;
import com.datn2021.repo.MenuRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;
import com.datn2021.repo.SalesRepository;
import com.datn2021.repo.StoreTableRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/invoice")
public class InvoiceController {
@Autowired private InvoiceRepository repo;
@Autowired private OrderFinalRepository finalRepo;
@Autowired private CustomerRepository cusRepo;
@Autowired private SalesRepository saleRepo;
@Autowired private OrderItemsRepository itemRepo;
@Autowired private MenuRepository menuRepo;
@Autowired private StoreTableRepository tableRepo;
	
	@GetMapping("")
	public List<Invoice> getListInvoice(){
		return repo.findAll();
	}
//	@GetMapping("/{id}")
//	public Optional<Invoice> getInvoiceById(@PathVariable Long id) {
//		return repo.findById(id);
//	}
	@GetMapping("/{id}")
	public Optional<Invoice> getInvoiceByOrderFinalId(@PathVariable Long id){
		return repo.findInvoiceWithOrderFinalId(id);
	}
	
	@PostMapping("")
	public ResponseEntity<Invoice> createInvoice(@RequestBody Map<String, String> dataInvoice){
		Invoice newInvoice = new Invoice();
		Long tbid = new Long(999);
		newInvoice.setTotal(BigDecimal.valueOf(Double.parseDouble(dataInvoice.get("total"))));
		newInvoice.setOderFinal(finalRepo.findById(Long.parseLong(dataInvoice.get("order_final_id"))).get());
		newInvoice.setCustomer(cusRepo.findById(Long.parseLong(dataInvoice.get("customer_id"))).get());
		if(null != dataInvoice.get("sale_id")) {
			newInvoice.setSale(saleRepo.findById(Long.parseLong(dataInvoice.get("sale_id"))).get());
		}
		newInvoice.setTotalSale(BigDecimal.valueOf(Double.parseDouble(dataInvoice.get("total_sale"))));
		newInvoice.setCreateDate(new Date());
		
		OrderFinal of = finalRepo.findById(newInvoice.getOderFinal().getId()).get();
		if(of.getStoreTable().getId() == tbid) {
			StoreTable table = tableRepo.findById(tbid).get();
			table.setStatus("OK");
			tableRepo.save(table);
		}
		of.setDelete(true);
		of.setSale(newInvoice.getSale());
		of.setTotal(newInvoice.getTotal());
		of.setInvoice(newInvoice);
		finalRepo.save(of);
		
		return new ResponseEntity<>(newInvoice, HttpStatus.OK);
	}
	
	@PostMapping("/totalbydatetodate")
	public ResponseEntity<BigDecimal> totalInvoiceByDateToDate(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			total = repo.findTotalByDateToDate(fromDate, toDate);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/totalbymonthtomonth")
	public ResponseEntity<BigDecimal> totalInvoiceByMonthToMonth(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			total = repo.findTotalByMonthToMonth(fromDate, toDate);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/totalbyyeartoyear")
	public ResponseEntity<BigDecimal> totalInvoiceByYearToYear(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			total = repo.findTotalByYearToYear(fromDate, toDate);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/totalbydate")
	public ResponseEntity<BigDecimal> totalInvoiceByDate(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			total = repo.findTotalByDate(date);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/totalbymonth")
	public ResponseEntity<BigDecimal> totalInvoiceByMonth(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			total = repo.findTotalByMonth(date);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/totalbyyear")
	public ResponseEntity<BigDecimal> totalInvoiceByYear(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			total = repo.findTotalByYear(date);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/listbydate")
	public ResponseEntity<List<Invoice>> ListInvoiceByDate(@RequestBody Map<String, Date> dataInvoice){
		List<Invoice> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			list = repo.findListInvoiceByDateToDate(fromDate, toDate);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbymonthtomonth")
	public ResponseEntity<List<Invoice>> ListInvoiceByMonthToMonth(@RequestBody Map<String, Date> dataInvoice){
		List<Invoice> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			list = repo.findListInvoiceByMonthToMonth(fromDate, toDate);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbyyeartoyear")
	public ResponseEntity<List<Invoice>> ListInvoiceByYearToYear(@RequestBody Map<String, Date> dataInvoice){
		List<Invoice> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			list = repo.findListInvoiceByYearToYear(fromDate, toDate);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbyonedate")
	public ResponseEntity<List<Invoice>> ListInvoiceByOneDate(@RequestBody Map<String, Date> dataInvoice){
		List<Invoice> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			list = repo.findListInvoiceByOneDate(date);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbyonemonth")
	public ResponseEntity<List<Invoice>> ListInvoiceByOneMonth(@RequestBody Map<String, Date> dataInvoice){
		List<Invoice> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			list = repo.findListInvoiceByOneMonth(date);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbyoneyear")
	public ResponseEntity<List<Invoice>> ListInvoiceByOneYear(@RequestBody Map<String, Date> dataInvoice){
		List<Invoice> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			list = repo.findListInvoiceByOneYear(date);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/countbydatetodate")
	public ResponseEntity<Long> CountInvoiceByDateToDate(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			count = repo.countInvoiceByDateToDate(fromDate, toDate);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/countbymonthtomonth")
	public ResponseEntity<Long> CountInvoiceByMonthToMonth(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			count = repo.countInvoiceByMonthToMonth(fromDate, toDate);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/countbyyeartoyear")
	public ResponseEntity<Long> CountInvoiceByYearToYear(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			count = repo.countInvoiceByYearToYear(fromDate, toDate);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/countbydate")
	public ResponseEntity<Long> CountInvoiceByDate(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			count = repo.countInvoiceByDate(date);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/countbymonth")
	public ResponseEntity<Long> CountInvoiceByMonth(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			count = repo.countInvoiceByMonth(date);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/countbyyear")
	public ResponseEntity<Long> CountInvoiceByYear(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			count = repo.countInvoiceByYear(date);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/detailinvoice")
	public ResponseEntity<List<OrderItems>> findDetailInvoiceById(@RequestBody Map<String, Long> dataInvoice){
		List<OrderItems> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Long id = dataInvoice.get("invoiceid");
			list = itemRepo.findOrderItemsByInvoiceId(id);
		}
		return new ResponseEntity<List<OrderItems>>(list,HttpStatus.OK);
	}
	
	@PostMapping("/bestseller")
	public ResponseEntity<Menu> getBestSellMenu(){
		Menu menu = menuRepo.getBestSeller();
		return new ResponseEntity<Menu>(menu,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public void deleteInvoice(@PathVariable Long id){
		Invoice invoice = repo.findById(id).get();
		invoice.setDelete(true);
	}

}
