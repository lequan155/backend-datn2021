package com.datn2021.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
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

import com.datn2021.dto.InvoiceDTO;
import com.datn2021.dto.OrderItemsDTO;
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
import com.datn2021.services.InvoiceService;

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
@Autowired private ModelMapper mapper;
@Autowired private InvoiceService invoiceService;
	
	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InvoiceDTO>> getListInvoice(){
		List<InvoiceDTO> list = invoiceService.getListInvoice();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
//	@GetMapping("/{id}")
//	public Optional<Invoice> getInvoiceById(@PathVariable Long id) {
//		return repo.findById(id);
//	}
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<InvoiceDTO> getInvoiceByOrderFinalId(@PathVariable Long id){
		InvoiceDTO invoice = invoiceService.getInvoiceByOrderFinalId(id);
		return new ResponseEntity<>(invoice,HttpStatus.OK);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody Map<String, String> dataInvoice){
		Invoice newInvoice = new Invoice();
		Long tbid = new Long(999);
		newInvoice.setTotal(BigDecimal.valueOf(Double.parseDouble(dataInvoice.get("total"))));
		newInvoice.setOderFinal(finalRepo.findById(Long.parseLong(dataInvoice.get("order_final_id"))).get());
		newInvoice.setCustomer(cusRepo.findById(Long.parseLong(dataInvoice.get("customer_id"))).get());
		if(null != dataInvoice.get("sale_id")) {
			newInvoice.setSale(saleRepo.findById(Long.parseLong(dataInvoice.get("sale_id"))).get());
		}
		if(null != dataInvoice.get("total_sale")) {
			newInvoice.setTotalSale(BigDecimal.valueOf(Double.parseDouble(dataInvoice.get("total_sale"))));
		}
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
		//of.setInvoice(newInvoice);
		finalRepo.save(of);
		repo.save(newInvoice);
		InvoiceDTO invoicedto = mapper.map(newInvoice, InvoiceDTO.class);
		return new ResponseEntity<>(invoicedto, HttpStatus.OK);
	}
	
	@PostMapping("/totalbydatetodate")
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BigDecimal> totalInvoiceByDate(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			total = repo.findTotalByDate(date);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/totalbymonth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BigDecimal> totalInvoiceByMonth(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			total = repo.findTotalByMonth(date);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/totalbyyear")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BigDecimal> totalInvoiceByYear(@RequestBody Map<String, Date> dataInvoice){
		BigDecimal total = new BigDecimal(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			total = repo.findTotalByYear(date);
		}
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	
	@PostMapping("/listbydate")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InvoiceDTO>> ListInvoiceByDate(@RequestBody Map<String, Date> dataInvoice){
		List<InvoiceDTO> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			list = invoiceService.ListInvoiceByDate(fromDate, toDate);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbymonthtomonth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InvoiceDTO>> ListInvoiceByMonthToMonth(@RequestBody Map<String, Date> dataInvoice){
		List<InvoiceDTO> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			list = invoiceService.ListInvoiceByMonthToMonth(fromDate, toDate);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbyyeartoyear")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InvoiceDTO>> ListInvoiceByYearToYear(@RequestBody Map<String, Date> dataInvoice){
		List<InvoiceDTO> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date fromDate = dataInvoice.get("fromDate");
			Date toDate = dataInvoice.get("toDate");
			list = invoiceService.ListInvoiceByYearToYear(fromDate, toDate);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/listbyonedate")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InvoiceDTO>> ListInvoiceByOneDate(@RequestBody Map<String, Date> dataInvoice){
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			listdto = invoiceService.ListInvoiceByOneDate(date);
		}
		return new ResponseEntity<>(listdto,HttpStatus.OK);
	}
	
	@PostMapping("/listbyonemonth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InvoiceDTO>> ListInvoiceByOneMonth(@RequestBody Map<String, Date> dataInvoice){
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			listdto = invoiceService.ListInvoiceByOneMonth(date);
		}
		return new ResponseEntity<>(listdto,HttpStatus.OK);
	}
	
	@PostMapping("/listbyoneyear")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InvoiceDTO>> ListInvoiceByOneYear(@RequestBody Map<String, Date> dataInvoice){
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			listdto = invoiceService.ListInvoiceByOneYear(date);
		}
		return new ResponseEntity<>(listdto,HttpStatus.OK);
	}
	
	@PostMapping("/countbydatetodate")
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Long> CountInvoiceByDate(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			count = repo.countInvoiceByDate(date);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/countbymonth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Long> CountInvoiceByMonth(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			count = repo.countInvoiceByMonth(date);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/countbyyear")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Long> CountInvoiceByYear(@RequestBody Map<String, Date> dataInvoice){
		Long count = new Long(0);
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Date date = dataInvoice.get("date");
			count = repo.countInvoiceByYear(date);
		}
		return new ResponseEntity<Long>(count,HttpStatus.OK);
	}
	
	@PostMapping("/detailinvoice")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<OrderItemsDTO>> findDetailInvoiceById(@RequestBody Map<String, Long> dataInvoice){
		List<OrderItemsDTO> list = new ArrayList<>();
		if(dataInvoice != null && !dataInvoice.isEmpty()) {
			Long id = dataInvoice.get("invoiceid");
			list = invoiceService.findDetailInvoiceById(id);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/bestseller")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Menu> getBestSellMenu(){
		Menu menu = menuRepo.getBestSeller();
		return new ResponseEntity<Menu>(menu,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteInvoice(@PathVariable Long id){
		Invoice invoice = repo.findById(id).get();
		invoice.setDelete(true);
	}

}
