package com.team2.lb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	

	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("test")
	public String test() {
		return "test";
	}
}
