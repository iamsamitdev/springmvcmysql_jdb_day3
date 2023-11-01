package com.itgenius.springmvcmysql_jdb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itgenius.springmvcmysql_jdb.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
