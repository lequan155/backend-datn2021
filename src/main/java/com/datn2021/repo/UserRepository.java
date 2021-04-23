package com.datn2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn2021.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "select * from User where user_name = ?1 and pass_word = ?2",nativeQuery = true) 
	public User findByUserName(String userName, String password);
}
