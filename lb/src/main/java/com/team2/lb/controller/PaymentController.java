package com.team2.lb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("payment2")
public class PaymentController {
	
	@GetMapping("toss")
	public String toss(String price, Model model) {
		model.addAttribute("price", price);
		log.debug("가격은:{}" ,price);
		return "payment/toss2";
	}
	
	@GetMapping("join")
	public String join() {
		return "bookBoard/join";
	}
	
	
	@GetMapping("success")
	public String success() {
		return "payment/success";
	}
	
	@GetMapping("pay")
	public String pay() {
		return "bookBoard/pay";
	}
}
