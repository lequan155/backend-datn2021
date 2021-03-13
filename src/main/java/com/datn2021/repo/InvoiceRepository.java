package com.datn2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn2021.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
