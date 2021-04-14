package com.datn2021.repo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.Invoice;
import com.datn2021.model.Menu;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	@Query(value = "select * from invoice where oder_final_id = ?1",nativeQuery = true) 
	public Invoice findByOderFinalId(Long oderFinalId);

	@Query(value = "select SUM(total) as totalAmount from invoice WHERE is_delete = 0 AND create_date BETWEEN ?1 AND ?2", nativeQuery = true)
	public BigDecimal findTotalByDate(Date fromDate, Date toDate);
	
	@Query(value = "SELECT m.*,"
			+ "(SELECT COUNT(menu_id) FROM order_items o WHERE o.menu_id = m.id AND o.is_active = 1 and o.is_delete = 0) as totalcount, "
			+ "(SELECT SUM(o.qty) from order_items o WHERE o.menu_id = m.id AND o.is_active = 1 and o.is_delete = 0 ) as qty "
			+ "FROM menu m LEFT JOIN order_items o on m.id = o.menu_id "
			+ "GROUP BY m.id ORDER BY qty DESC limit 1" ,nativeQuery = true)
	public Menu getBestSeller();
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND i.create_date BETWEEN ?1 AND ?2",nativeQuery = true)
	public List<Invoice> findListInvoiceByDateToDate (Date fromDate, Date toDate);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND DATE(i.create_date) = DATE(?1)",nativeQuery = true)
	public List<Invoice> findListInvoiceByOneDate (Date date);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND MONTH(i.create_date) = MONTH(?1)",nativeQuery = true)
	public List<Invoice> findListInvoiceByOneMonth (Date date);
	
	@Query(value = "select i.* from invoice i WHERE i.is_delete = 0 AND YEAR(i.create_date) = YEAR(?1)",nativeQuery = true)
	public List<Invoice> findListInvoiceByOneYear (Date date);
}
