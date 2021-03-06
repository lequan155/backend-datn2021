package com.datn2021.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.TableNotification;

public interface NotificationRepository extends JpaRepository<TableNotification, Long> {
	@Query(value="select * from table_notification order by date desc limit 10",nativeQuery = true)
	public List<TableNotification> getTopNotification();
}
