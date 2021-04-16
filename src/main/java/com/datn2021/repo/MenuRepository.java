package com.datn2021.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>{
	@Query(value="select m.* from menu m where m.id = ?1", nativeQuery = true)
	Menu findMenuItemById(Long id);
	
	@Query(value="select m.* from menu m where m.types_id =?1", nativeQuery = true)
	public List<Menu> findByTypes(Long type);
	
	@Query(value="select m.* from menu m where m.types_id =?1 and is_active = 1", nativeQuery = true)
	public List<Menu> showMenu(Long type);

	@Query(value = "SELECT m.*,"
			+ "(SELECT COUNT(menu_id) FROM order_items o WHERE o.menu_id = m.id AND o.is_active = 1 and o.is_delete = 0) as totalcount, "
			+ "(SELECT SUM(o.qty) from order_items o WHERE o.menu_id = m.id AND o.is_active = 1 and o.is_delete = 0 ) as qty "
			+ "FROM menu m LEFT JOIN order_items o on m.id = o.menu_id "
			+ "GROUP BY m.id ORDER BY qty DESC limit 1" ,nativeQuery = true)
	public Menu getBestSeller();
}
