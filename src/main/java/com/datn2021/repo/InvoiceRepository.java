package com.datn2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	@Query(value = "select * from invoice where oder_final_id = ?1",nativeQuery = true) 
	public Invoice findByOderFinalId(Long oderFinalId);
}
