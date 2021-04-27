package com.datn2021.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Role {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
	
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return roleName;
//	}
	public Role() {
		
	}
	public Role(ERole name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public ERole getName() {
		return name;
	}
	public void setName(ERole name) {
		this.name = name;
	}
	
}
