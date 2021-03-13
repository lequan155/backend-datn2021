package com.datn2021.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Invoice {
	@Id @GeneratedValue
	private Long id;
	private Long oderFinalId;
	private Long total;
	private String saleId;
	private String branchId;
}
