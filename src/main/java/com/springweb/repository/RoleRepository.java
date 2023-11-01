package com.springweb.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springweb.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole(String role);
	
	@Query("SELECT r FROM Role r WHERE r.role = ?1")
	Set<Role> findByRoles(String role);
	
	@Query(value = "SELECT r FROM Role r WHERE r.role IN :roleNames")
	Set<Role> findRolesByNameSet(@Param("roleNames") Set<String> roleNames);
}
