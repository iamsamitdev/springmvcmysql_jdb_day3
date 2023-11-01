package com.itgenius.springmvcmysql_jdb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itgenius.springmvcmysql_jdb.model.ERole;
import com.itgenius.springmvcmysql_jdb.model.Role;

public interface  RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}