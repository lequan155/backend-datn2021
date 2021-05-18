package com.datn2021.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
	private String from;
	private String message;
	private String tableId;
}
