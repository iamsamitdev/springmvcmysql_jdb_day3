package com.itgenius.springmvcmysql_jdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/backend")
public class BackendController {

    String viewpath = "backend/";

	@GetMapping(value = "/dashboard")
	public String dashboard() {
		return viewpath + "dashboard";
	}
    
}
