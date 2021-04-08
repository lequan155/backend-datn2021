package com.datn2021.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.StoreTable;

public interface StoreTableRepository extends JpaRepository<StoreTable, Long> {
	@Query(value = "select * from store_table Limit ?1",nativeQuery = true)
	public List<StoreTable> findAll(int limit);
}
