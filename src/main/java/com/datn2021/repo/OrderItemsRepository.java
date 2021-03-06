package com.datn2021.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Menu;
import com.datn2021.model.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
	@Query(value = "select i.* from order_items i left join order_final f on i.order_final_id = f.id where f.store_table_id = ?1 and f.is_delete = 0 and i.is_active = 1",nativeQuery = true) 
	List<OrderItems> findByTableId(Long id);
	
	@Query(value = "select distinct f.id from order_items i left join order_final f on i.order_final_id = f.id where f.store_table_id = ?1", nativeQuery = true)
	Long findFinalOrderId(Long id);
	
	@Query(value = "select i.* from order_items i left join order_final f on i.order_final_id = f.id where i.order_final_id = ?1 and i.is_delete = 0 and i.is_active = 1", nativeQuery = true)
	List<OrderItems> findActiveByOrderFinalId(Long id);
	
	@Query(value = "select i.* from order_items i left join order_final f on i.order_final_id = f.id where i.order_final_id = ?1 and i.is_delete = 0", nativeQuery = true)
	List<OrderItems> findAllByOrderFinalId(Long id);
	
	@Query(value = "select i.* from order_items i where i.is_active = 1 and i.is_delete = 0 and i.menu_id = ?1 and i.order_final_id = ?2", nativeQuery = true)
	OrderItems findByMenuId(Long id,Long fid);
	
	@Query(value = "select i.* from order_items i where i.is_active = 0 and i.is_delete = 0 and i.menu_id = ?1 and i.order_final_id = ?2", nativeQuery = true)
	OrderItems findCancelItemByMenuId(Long id,Long fid);
	
	@Query(value = "select i.* from order_items i left join order_final f on i.order_final_id = f.id where i.order_final_id = ?1 and i.is_delete = 0 and i.is_active = 0", nativeQuery = true)
	List<OrderItems> listCancelItemsByOrderFinalId(Long id);
	
	@Query(value = "select i.* from order_items i LEFT JOIN order_final o ON i.order_final_id = o.id LEFT JOIN invoice iv ON o.id = iv.oder_final_id WHERE i.is_delete = 0 AND i.is_active = 1 AND iv.id = ?1",nativeQuery = true)
	public List<OrderItems> findOrderItemsByInvoiceId(Long id);
}
