package com.datn2021.payload.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
	private String from;
	private String message;
	private String tableId;
	private Date date=new Date();
}
