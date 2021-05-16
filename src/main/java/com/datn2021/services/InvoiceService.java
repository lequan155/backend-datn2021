package com.datn2021.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.datn2021.dto.InvoiceDTO;
import com.datn2021.dto.OrderItemsDTO;
import com.datn2021.model.Invoice;
import com.datn2021.model.Menu;
import com.datn2021.model.OrderItems;
import com.datn2021.repo.CustomerRepository;
import com.datn2021.repo.InvoiceRepository;
import com.datn2021.repo.MenuRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;
import com.datn2021.repo.SalesRepository;
import com.datn2021.repo.StoreTableRepository;

@Service
public class InvoiceService {
	@Autowired private InvoiceRepository repo;
	@Autowired private OrderFinalRepository finalRepo;
	@Autowired private CustomerRepository cusRepo;
	@Autowired private SalesRepository saleRepo;
	@Autowired private OrderItemsRepository itemRepo;
	@Autowired private MenuRepository menuRepo;
	@Autowired private StoreTableRepository tableRepo;
	@Autowired private ModelMapper mapper;
	
	public List<InvoiceDTO> getListInvoice(){
		List<Invoice> list = repo.findAll();
		List<InvoiceDTO> listdto = new ArrayList<>();
		for(Invoice i : list) {
			InvoiceDTO invoice = mapper.map(i, InvoiceDTO.class);
			listdto.add(invoice);
		}
		return listdto;
	}
	
	public InvoiceDTO getInvoiceByOrderFinalId(Long id){
		Invoice invoice = repo.findInvoiceWithOrderFinalId(id).get();
		InvoiceDTO invoicedto = mapper.map(invoice, InvoiceDTO.class);
		return invoicedto;
	}
	
	public List<InvoiceDTO> ListInvoiceByDate(Date fromDate, Date toDate){
		List<Invoice> list = new ArrayList<>();
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(null != fromDate && null != toDate) {
			list = repo.findListInvoiceByDateToDate(fromDate, toDate);
			for(Invoice i : list) {
				InvoiceDTO invoice = mapper.map(i, InvoiceDTO.class);
				listdto.add(invoice);
			}
		}
		return listdto;
	}
	
	public List<InvoiceDTO> ListInvoiceByMonthToMonth(Date fromDate, Date toDate){
		List<Invoice> list = new ArrayList<>();
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(null != fromDate && null != toDate) {
			list = repo.findListInvoiceByMonthToMonth(fromDate, toDate);
			for(Invoice i : list) {
				InvoiceDTO invoice = mapper.map(i, InvoiceDTO.class);
				listdto.add(invoice);
			}
		}
		return listdto;
	}
	
	public List<InvoiceDTO> ListInvoiceByYearToYear(Date fromDate, Date toDate){
		List<Invoice> list = new ArrayList<>();
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(null != fromDate && null != toDate) {
			list = repo.findListInvoiceByYearToYear(fromDate, toDate);
			for(Invoice i : list) {
				InvoiceDTO invoice = mapper.map(i, InvoiceDTO.class);
				listdto.add(invoice);
			}
		}
		return listdto;
	}
	
	public List<InvoiceDTO> ListInvoiceByOneDate(Date date){
		List<Invoice> list = new ArrayList<>();
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(null != date) {
			list = repo.findListInvoiceByOneDate(date);
			for(Invoice i : list) {
				InvoiceDTO invoice = mapper.map(i, InvoiceDTO.class);
				listdto.add(invoice);
			}
		}
		return listdto;
	}
	
	public List<InvoiceDTO> ListInvoiceByOneMonth(Date date){
		List<Invoice> list = new ArrayList<>();
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(null != date) {
			list = repo.findListInvoiceByOneMonth(date);
			for(Invoice i : list) {
				InvoiceDTO invoice = mapper.map(i, InvoiceDTO.class);
				listdto.add(invoice);
			}
		}
		return listdto;
	}
	
	public List<InvoiceDTO> ListInvoiceByOneYear(Date date){
		List<Invoice> list = new ArrayList<>();
		List<InvoiceDTO> listdto = new ArrayList<>();
		if(null != date) {
			list = repo.findListInvoiceByOneYear(date);
			for(Invoice i : list) {
				InvoiceDTO invoice = mapper.map(i, InvoiceDTO.class);
				listdto.add(invoice);
			}
		}
		return listdto;
	}
	
	public List<OrderItemsDTO> findDetailInvoiceById(Long id){
		List<OrderItems> list = new ArrayList<>();
		List<OrderItemsDTO> listdto = new ArrayList<>();
		if(null != id) {
			list = itemRepo.findOrderItemsByInvoiceId(id);
			for(OrderItems i : list) {
				OrderItemsDTO item = mapper.map(i, OrderItemsDTO.class);
				listdto.add(item);
			}
		}
		return listdto;
	}
	
}
