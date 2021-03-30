package com.datn2021.dto;

import java.math.BigDecimal;
import java.util.List;

import com.datn2021.model.Customer;
import com.datn2021.model.Invoice;
import com.datn2021.model.OrderItems;
import com.datn2021.model.Sales;
import com.datn2021.model.StoreTable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderFinalDTO {
	private Long id;
	private Customer customer;
	private StoreTable storeTable;
	private Invoice invoice;
	private List<OrderItems> listOrderItems;
	private Sales sale;
	private BigDecimal total;
	private boolean isDelete;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public StoreTable getStoreTable() {
		return storeTable;
	}
	public void setStoreTable(StoreTable storeTable) {
		this.storeTable = storeTable;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public List<OrderItems> getListOrderItems() {
		return listOrderItems;
	}
	public void setListOrderItems(List<OrderItems> listOrderItems) {
		this.listOrderItems = listOrderItems;
	}
	public Sales getSale() {
		return sale;
	}
	public void setSale(Sales sale) {
		this.sale = sale;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
