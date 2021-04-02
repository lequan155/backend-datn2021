package com.datn2021.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
	@Query(value = "select i.* from order_items i left join order_final f on i.order_final_id = f.id where f.store_table_id = ?1 and f.is_delete = 0",nativeQuery = true) 
	List<OrderItems> findByTableId(Long id);
	
	@Query(value = "select distinct f.id from order_items i left join order_final f on i.order_final_id = f.id where f.store_table_id = ?1", nativeQuery = true)
	Long findFinalOrderId(Long id);
	
	@Query(value = "select i.* from order_items i left join order_final f on i.order_final_id = f.id where i.order_final_id = ?1", nativeQuery = true)
	List<OrderItems> findByOrderFinalId(Long id);
}
