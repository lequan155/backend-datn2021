package com.datn2021.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class OrderItems {
	@Id @GeneratedValue
	private Long id;
	@ManyToOne
	@JoinColumn(name = "menu_id",referencedColumnName = "id")
	private Menu Item;
	private boolean status = false;
	private String note;
	private int qty;
	@ManyToOne
	@JoinColumn(name = "orderFinal_id",referencedColumnName = "id")
	private OrderFinal orderFinal;
	private boolean isActive;
	private boolean isDelete;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Menu getItem() {
		return Item;
	}
	public void setItem(Menu item) {
		Item = item;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public OrderFinal getOrderFinal() {
		return orderFinal;
	}
	public void setOrderFinal(OrderFinal orderFinal) {
		this.orderFinal = orderFinal;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
