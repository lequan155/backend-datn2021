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

import com.datn2021.model.Sales;
import com.datn2021.repo.SalesRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/sales")
public class SalesController {
@Autowired private SalesRepository repo;
	
	@GetMapping("")
	public List<Sales> getListSales(){
		return repo.findAll();
	}
	
	@PostMapping("")
	public Sales createSales(@RequestBody Sales newSales){
		return repo.save(newSales);
	
	}@PutMapping("/{id}")
	public Sales updateSales(@RequestBody Sales newSales, @PathVariable Long id){
		return repo.findById(id).map(
				Sales -> {
//					Sales.setId(newSales.getId());
					Sales.setSalesName(newSales.getSalesName());
					Sales.setDiscountAmount(newSales.getDiscountAmount());
					Sales.setDescription(newSales.getDescription());
					return repo.save(Sales);
				}).orElseGet(()->{
					newSales.setId(id);
					return repo.save(newSales);
				});
	}
	@DeleteMapping("/{id}")
	public void deleteSales(@PathVariable Long id){
		repo.deleteById(id);;
	}

}
