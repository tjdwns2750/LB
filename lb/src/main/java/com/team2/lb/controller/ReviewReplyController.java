package com.team2.lb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team2.lb.service.ReviewReplyService;
import com.team2.lb.vo.ReviewReply;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("reply")
public class ReviewReplyController {
	
	@Autowired
	ReviewReplyService rService;
	
	@PostMapping("writeReply")
	public String writeReply(ReviewReply reply, @AuthenticationPrincipal UserDetails user) {
		reply.setId(user.getUsername());
		int result = rService.writeReply(reply);
		return "redirect:/board/read?bno=" + reply.getBno();
	}
	@GetMapping("deleteReply")
	public String deleteReply(ReviewReply reply, @AuthenticationPrincipal UserDetails user) {
		reply.setId(user.getUsername());
		int result = rService.deleteReply(reply);
		return "redirect:/board/read?bno=" + reply.getBno();
	}
}
