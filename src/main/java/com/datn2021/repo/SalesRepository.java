package com.datn2021.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Sales;
import com.datn2021.model.StoreTable;

public interface SalesRepository extends JpaRepository<Sales, Long> {
	@Query(value = "select * from sales where is_active = true",nativeQuery = true)
	public List<Sales> findListSalesActive();
}
