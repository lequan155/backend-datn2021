package com.datn2021.dto;

import java.util.Date;

import com.datn2021.model.Customer;
import com.datn2021.model.OrderFinal;
import com.datn2021.model.Sales;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class InvoiceDTO {
	private Long id;
	@JsonIgnoreProperties("listOrderItems")
	private OrderFinal oderFinal;
	private Long total;
	private Long totalSale;
	private Sales sale;
	private Customer customer;
	private Date createDate;
	private boolean isDelete;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OrderFinal getOderFinal() {
		return oderFinal;
	}
	public void setOderFinal(OrderFinal oderFinal) {
		this.oderFinal = oderFinal;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Sales getSale() {
		return sale;
	}
	public void setSale(Sales sale) {
		this.sale = sale;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Long getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(Long totalSale) {
		this.totalSale = totalSale;
	}
	
	
}
