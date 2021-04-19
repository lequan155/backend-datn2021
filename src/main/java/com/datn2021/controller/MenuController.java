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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.model.Menu;
import com.datn2021.repo.MenuRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/menu")
public class MenuController {
@Autowired private MenuRepository repo;
	
	@GetMapping("")
	public List<Menu> getListMenu(){
		return repo.findAll();
	}
	
	@GetMapping("/types/{id}")
	public List<Menu> showListMenuByTypes(@PathVariable Long id){
		return repo.showMenu(id);
	}
	
	@GetMapping("/crudmenu/{id}")
	public List<Menu> getListMenuByTypes(@PathVariable Long id){
		return repo.findByTypes(id);
	}
	
	@PostMapping("")
	public Menu createMenu(@RequestBody Menu newMenu){
		if(newMenu.getId() == null) {
			newMenu.setId(new Long(0));
		}
		return repo.save(newMenu);
	}
	
	@PostMapping("/deactive")
	public void deActiveMenu(@RequestBody(required = true) List<Long> list) {
		try {
			if(!list.isEmpty()) {
				for(Long id : list) {
					Menu item = repo.findById(id).get();
					item.setActive(false);
					repo.save(item);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@PostMapping("/addnew")
	public Menu addNewMenu(@RequestParam(value = "name", required = true) String name) {
		name = "abcd";
		return null;
	}
	
	@GetMapping("/{id}")
	public Menu getMenuById(@PathVariable Long id) {
		return repo.findMenuItemById(id);
	}
	
	@PutMapping("/{id}")
	public Menu updateMenu(@RequestBody Menu newMenu, @PathVariable Long id){
		return repo.findById(id).map(
				menu -> {
					menu.setName(newMenu.getName());
					menu.setPrice(newMenu.getPrice());
					menu.setPicture(newMenu.getPicture());
					menu.setType(newMenu.getType());
					menu.setStatus(newMenu.isStatus());
					menu.setActive(newMenu.isActive());
					return repo.save(menu);
				}).orElseGet(()->{
					newMenu.setId(id);
					return repo.save(newMenu);
				});
	}
	@DeleteMapping("/{id}")
	public void deleteMenu(@PathVariable Long id){
		Menu menu = repo.findById(id).get();
		menu.setDelete(true);
	}

}
