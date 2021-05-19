package com.datn2021.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TableNotification {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tableid;
	private String fromTable;
	private String message;
	private Date date;
	private boolean isRead = false;
}
