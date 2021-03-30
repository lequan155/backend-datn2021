package com.datn2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>{
	@Query(value="select m.* from menu m where m.id = ?1", nativeQuery = true)
	Menu findMenuItemById(Long id);
}
