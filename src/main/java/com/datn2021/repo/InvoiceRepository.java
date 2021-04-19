package com.datn2021.repo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Invoice;
import com.datn2021.model.Menu;
import com.datn2021.model.OrderItems;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	@Query(value = "select * from invoice where oder_final_id = ?1",nativeQuery = true) 
	public Invoice findByOderFinalId(Long oderFinalId);

	@Query(value = "select SUM(total) as totalAmount from invoice WHERE is_delete = 0 AND create_date BETWEEN ?1 AND ?2", nativeQuery = true)
	public BigDecimal findTotalByDateToDate(Date fromDate, Date toDate);
	
	@Query(value = "select SUM(total) as totalAmount from invoice WHERE is_delete = 0 AND MONTH(create_date) BETWEEN MONTH(?1) AND MONTH(?2)", nativeQuery = true)
	public BigDecimal findTotalByMonthToMonth(Date fromDate, Date toDate);
	
	@Query(value = "select SUM(total) as totalAmount from invoice WHERE is_delete = 0 AND YEAR(create_date) BETWEEN YEAR(?1) AND YEAR(?2)", nativeQuery = true)
	public BigDecimal findTotalByYearToYear(Date fromDate, Date toDate);
	
	@Query(value = "select SUM(total) as totalAmount from invoice WHERE is_delete = 0 AND DATE(create_date) = DATE(?1)", nativeQuery = true)
	public BigDecimal findTotalByDate(Date date);
	
	@Query(value = "select SUM(total) as totalAmount from invoice WHERE is_delete = 0 AND MONTH(create_date) = MONTH(?1)", nativeQuery = true)
	public BigDecimal findTotalByMonth(Date date);
	
	@Query(value = "select SUM(total) as totalAmount from invoice WHERE is_delete = 0 AND YEAR(create_date) = YEAR(?1)", nativeQuery = true)
	public BigDecimal findTotalByYear(Date date);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND i.create_date BETWEEN ?1 AND ?2",nativeQuery = true)
	public List<Invoice> findListInvoiceByDateToDate (Date fromDate, Date toDate);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND MONTH(i.create_date) BETWEEN MONTH(?1) AND MONTH(?2)",nativeQuery = true)
	public List<Invoice> findListInvoiceByMonthToMonth (Date fromDate, Date toDate);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND YEAR(i.create_date) BETWEEN YEAR(?1) AND YEAR(?2)",nativeQuery = true)
	public List<Invoice> findListInvoiceByYearToYear (Date fromDate, Date toDate);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND DATE(i.create_date) = DATE(?1)",nativeQuery = true)
	public List<Invoice> findListInvoiceByOneDate (Date date);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND MONTH(i.create_date) = MONTH(?1)",nativeQuery = true)
	public List<Invoice> findListInvoiceByOneMonth (Date date);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND YEAR(i.create_date) = YEAR(?1)",nativeQuery = true)
	public List<Invoice> findListInvoiceByOneYear (Date date);
	
	@Query(value = "select COUNT(i.id) as CountInvoice from invoice i WHERE i.is_delete = 0 AND i.create_date BETWEEN ?1 AND ?2",nativeQuery = true)
	public Long countInvoiceByDateToDate(Date fromDate, Date toDate);

	@Query(value = "select COUNT(i.id) as CountInvoice from invoice i WHERE i.is_delete = 0 AND MONTH(i.create_date) BETWEEN MONTH(?1) AND MONTH(?2)",nativeQuery = true)
	public Long countInvoiceByMonthToMonth(Date fromDate, Date toDate);
	
	@Query(value = "select COUNT(i.id) as CountInvoice from invoice i WHERE i.is_delete = 0 AND YEAR(i.create_date) BETWEEN YEAR(?1) AND YEAR(?2)",nativeQuery = true)
	public Long countInvoiceByYearToYear(Date fromDate, Date toDate);
	
	@Query(value = "select COUNT(i.id) as CountInvoice from invoice i WHERE i.is_delete = 0 AND DATE(i.create_date) = DATE(?1)",nativeQuery = true)
	public Long countInvoiceByDate(Date date);
	
	@Query(value = "select COUNT(i.id) as CountInvoice from invoice i WHERE i.is_delete = 0 AND MONTH(i.create_date) = MONTH(?1)",nativeQuery = true)
	public Long countInvoiceByMonth(Date date);
	
	@Query(value = "select COUNT(i.id) as CountInvoice from invoice i WHERE i.is_delete = 0 AND YEAR(i.create_date) = YEAR(?1)",nativeQuery = true)
	public Long countInvoiceByYear(Date date);
	
}
