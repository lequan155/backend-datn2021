package com.datn2021.controller;

public class MenuNotFoundException extends RuntimeException{
	public MenuNotFoundException(Long id) {
		super("Could not find item in menu" + id);
	}
}
