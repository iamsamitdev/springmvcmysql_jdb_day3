package com.itgenius.springmvcmysql_jdb.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.itgenius.springmvcmysql_jdb.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
