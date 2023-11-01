package com.itgenius.springmvcmysql_jdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itgenius.springmvcmysql_jdb.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    
}
