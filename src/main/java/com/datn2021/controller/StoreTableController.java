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

import com.datn2021.model.StoreTable;
import com.datn2021.repo.StoreTableRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/table")
public class StoreTableController {
	
	@Autowired private StoreTableRepository repo;
	
	@PostMapping("/index/{limit}")
	public List<StoreTable> getListTable(@PathVariable int limit){
		return repo.findAll(limit);
	}
	
	@GetMapping("/{id}")
	public StoreTable getTable(@PathVariable Long id) {
		return repo.findById(id).orElseThrow(()->new StoreTableNotFoundException(id));
	}
	
	@PostMapping("")
	public StoreTable createTable(@RequestBody StoreTable newTable){
		if(newTable.getId()==null) {
			newTable.setId(new Long(0));
		}
		return repo.save(newTable);
	
	}@PutMapping("/{id}")
	public StoreTable updateTable(@RequestBody StoreTable newTable, @PathVariable Long id){
		return repo.findById(id).map(
				storeTable -> {
//					storeTable.setId(newTable.getId());
					storeTable.setTableName(newTable.getTableName());
					storeTable.setStatus(newTable.getStatus());
					return repo.save(storeTable);
				}).orElseGet(()->{
					newTable.setId(id);
					return repo.save(newTable);
				});
	}
	
	@PutMapping("/updateStatusBusy/{id}")
	public StoreTable updateStatusTable(@RequestBody StoreTable newTable, @PathVariable Long id) {
		return repo.findById(id).map(
				storeTable -> {
					storeTable.setStatus("Busy");
					return repo.save(storeTable);
				}).orElseGet(()->{
					newTable.setId(id);
					return repo.save(newTable);
				});
	}
	@PutMapping("/updateStatusPending/{id}")
	public StoreTable updateStatusBusy(@RequestBody StoreTable newTable, @PathVariable Long id) {
		return repo.findById(id).map(
				storeTable -> {
					storeTable.setStatus("Pending");
					return repo.save(storeTable);
				}).orElseGet(()->{
					newTable.setId(id);
					return repo.save(newTable);
				});
	}
	@PutMapping("/updateStatusReady/{id}")
	public StoreTable updateStatusReady(@RequestBody StoreTable newTable, @PathVariable Long id) {
		
		return repo.findById(id).map(
				storeTable -> {
					storeTable.setStatus("Ready");
					return repo.save(storeTable);
				}).orElseGet(()->{
					newTable.setId(id);
					return repo.save(newTable);
				});
	}
	
	@DeleteMapping("/{id}")
	public void deleteTable(@PathVariable Long id){
		StoreTable newStoreTable = repo.findById(id).get();
		newStoreTable.setDelete(true);
		repo.save(newStoreTable);
	}
}
