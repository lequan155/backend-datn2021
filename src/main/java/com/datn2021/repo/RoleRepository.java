package com.datn2021.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn2021.model.ERole;
import com.datn2021.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole role);
}
