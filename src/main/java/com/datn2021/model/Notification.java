package com.datn2021.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
	private String from;
	private String message;
	private String tableId;
	private Date date = new Date();
}
