package com.team2.lb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.lb.service.BookBoardService;
import com.team2.lb.service.ChatService;
import com.team2.lb.service.MemberService;
import com.team2.lb.vo.BookBoard;
import com.team2.lb.vo.ChatMessage;
import com.team2.lb.vo.ChatRoom;
import com.team2.lb.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@Autowired
	MemberService service;

	@Autowired
	BookBoardService bookboardservice;

	@Autowired
	ChatService chatservice;

	// 메인 페이지
	@GetMapping("/")
	public String home(Model model, @AuthenticationPrincipal UserDetails user, ChatRoom chatRoom) {
		if (user != null) {
			log.info(user.getUsername());
			Member member = service.selectUser(user.getUsername());
			model.addAttribute("member", member);
		}
		return "home";

	}

	@GetMapping("findBoard")
	public String hometest(Model model, @AuthenticationPrincipal UserDetails user) {
		if (user != null) {
			log.info(user.getUsername());
			Member member = service.selectUser(user.getUsername());
			model.addAttribute("member", member);
			log.error("member : {}", member.getAddress());
		}
		// 계시판 전체 조회
		ArrayList<BookBoard> boardList = bookboardservice.showBoardList();
		log.info("boardlist {}", boardList);

		// 지도에 계시판을 출력하기 위해 json형식으로 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		String boardListJson = "[]"; // 기본값으로 빈 배열을 설정

		try {
			boardListJson = objectMapper.writeValueAsString(boardList);
		} catch (JsonProcessingException e) {
			log.error("Error converting boardList to JSON", e);
		}

		model.addAttribute("boardListJson", boardListJson);
		log.info("boardListJSON {}", boardListJson);
		model.addAttribute("boardlist", boardList);
		return "jinu/findBoard";
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

	@GetMapping("exam")
	public String exam() {
		return "chat/exam";
	}
}
