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
	
	@GetMapping("hometest")
	public String hometest() {
		return "jinu/home";
	}
	
	@GetMapping("layout")
	public String layout() {
		return "jinu/layout";
	}
	
	@GetMapping("test")
	public String test() {
		return "jinu/test";
	}
	
	@GetMapping("test2")
	public String test2() {
		return "jinu/index";
	}
	
	@GetMapping("about")
	public String about() {
		return "jinu/about";
	}
	
	@GetMapping("service")
	public String service() {
		return "jinu/service";
	}
	
	@GetMapping("package")
	public String packages() {
		return "jinu/package";
	}
	
	@GetMapping("destination")
	public String destination() {
		return "jinu/package";
	}
	
	@GetMapping("testimonial")
	public String testimontial() {
		return "jinu/testimonial";
	}
	
	@GetMapping("team")
	public String contact() {
		return "jinu/team";
	}
	
	@GetMapping("booking")
	public String booking() {
		return "jinu/booking";
	}
}
