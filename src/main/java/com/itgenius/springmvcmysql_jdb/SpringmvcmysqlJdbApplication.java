package com.itgenius.springmvcmysql_jdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.itgenius.springmvcmysql_jdb")
public class SpringmvcmysqlJdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmvcmysqlJdbApplication.class, args);
	}

}
