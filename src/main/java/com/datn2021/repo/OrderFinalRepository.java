package com.datn2021.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Customer;
import com.datn2021.model.OrderFinal;

public interface OrderFinalRepository extends JpaRepository<OrderFinal, Long> {
	@Query(value = "select * from order_final where store_table_id = ?1 and is_delete = 0", nativeQuery = true)
	public OrderFinal findByTableId(Long id);
	
	@Query(value = "select * from customer c left join order_final o on o.customer_id = c.id where o.id =?1", nativeQuery = true)
	public Customer findCustomerByOrderFinal (Long id);
}
