package com.datn2021.dto;


import com.datn2021.model.Menu;
import com.datn2021.model.OrderFinal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemsDTO {
	private Long id;
	private Menu Item;
	private boolean status = false;
	private String note;
	private int qty;
	private OrderFinal orderFinal;
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
	
}
