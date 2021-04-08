package com.datn2021.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderFinal {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id",referencedColumnName = "id")
	private Customer customer;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "storeTable_id",referencedColumnName = "id")
	private StoreTable storeTable;
	@OneToOne(cascade = CascadeType.ALL)
	private Invoice invoice;
	@OneToMany(mappedBy = "orderFinal",cascade = CascadeType.ALL)
	private List<OrderItems> listOrderItems;
	@OneToOne(cascade = CascadeType.ALL)
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
//	public List<OrderItems> getListOrderItems() {
//		return listOrderItems;
//	}
//	public void setListOrderItems(List<OrderItems> listOrderItems) {
//		this.listOrderItems = listOrderItems;
//	}
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
