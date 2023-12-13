package com.team2.lb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team2.lb.service.MemberService;
import com.team2.lb.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	MemberService service;
	
	@GetMapping("join")
	public String joinForm() {
		return "member/joinForm";
	}

	@PostMapping("join")
	public String join(Member member) {
		log.debug("join_param: {}", member);
		service.joinMember(member);
		return "redirect:/";
	}
	
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
}
