package com.datn2021.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Sales {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String salesName;
	private BigDecimal discountAmount;
	private boolean isDelete;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
