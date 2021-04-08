package com.datn2021.model;

import java.util.List;

import javax.persistence.Entity;
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
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String roleName;
	private boolean isDelete;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	
}
