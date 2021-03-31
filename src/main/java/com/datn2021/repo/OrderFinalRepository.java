package com.datn2021.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.OrderFinal;

public interface OrderFinalRepository extends JpaRepository<OrderFinal, Long> {
//	@Query(value = "select * from pending_order where table_id= ?1", nativeQuery = true)
//	List<PendingOrder> findItemWithTableId(Long id);
}
