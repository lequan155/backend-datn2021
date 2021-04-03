package com.datn2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query(value = "select * from customer where phone_no = ?1",nativeQuery = true)
	public Customer findByPhoneNo(String phoneNo);
	
	@Query(value = "select c.* from customer c left join order_final o on o.customer_id = c.id where o.id =?1", nativeQuery = true)
	public Customer findCustomerByOrderFinal (Long id);
}
