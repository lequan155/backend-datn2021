package com.datn2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query(value = "select * from customer where phone_no = ?1",nativeQuery = true)
	public Customer findByPhoneNo(String phoneNo);
}
