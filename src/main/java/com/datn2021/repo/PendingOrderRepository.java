package com.datn2021.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.PendingOrder;

public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {
	@Query(value = "select * from pending_order where table_id = ?1",nativeQuery = true) 
	List<PendingOrder> findAll(Long id);
	
	@Query(value = "select * from pending_order where table_id = ?1", nativeQuery = true)
	List<PendingOrder> findOrderTableById(Long id);
}
