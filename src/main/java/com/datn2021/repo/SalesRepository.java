package com.datn2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn2021.model.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long> {

}
