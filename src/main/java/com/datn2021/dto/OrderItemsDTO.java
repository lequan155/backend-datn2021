package com.datn2021.dto;


import com.datn2021.model.Menu;
import com.datn2021.model.OrderFinal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemsDTO {
	private Long id;
	private Menu Item;
//	private boolean status = false;
	private String note;
	private int qty;
	@JsonIgnoreProperties("listOrderItems")
	private OrderFinalDTO orderFinal;
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
	public OrderFinalDTO getOrderFinal() {
		return orderFinal;
	}
	public void setOrderFinal(OrderFinalDTO orderFinal) {
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
