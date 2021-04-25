package com.datn2021.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Invoice {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	private OrderFinal oderFinal;
	private BigDecimal total;
	@OneToOne(cascade = CascadeType.ALL)
	private Sales sale;
	private BigDecimal totalSale;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="customer_id",referencedColumnName = "id")
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
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Sales getSale() {
		return sale;
	}
	public void setSale(Sales sale) {
		this.sale = sale;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public BigDecimal getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(BigDecimal totalSale) {
		this.totalSale = totalSale;
	}
	
	
}
