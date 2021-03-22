package com.datn2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.datn2021.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
