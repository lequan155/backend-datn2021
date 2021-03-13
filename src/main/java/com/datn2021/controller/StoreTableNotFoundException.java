package com.datn2021.controller;

public class StoreTableNotFoundException extends RuntimeException {
	public StoreTableNotFoundException(Long id) {
		super("Could not find store table" + id);
	}
}
