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
import com.team2.lb.service.BoardService;
import com.team2.lb.service.BookBoardService;
import com.team2.lb.service.ChatService;
import com.team2.lb.service.MemberService;
import com.team2.lb.vo.Board;
import com.team2.lb.vo.BookBoard;
import com.team2.lb.vo.ChatRoom;
import com.team2.lb.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	// memberService에 의존성 주입
	@Autowired
	MemberService service;

	// bookboardService에 의존성 주입
	@Autowired
	BookBoardService bookboardservice;

	// chatService에 의존성 주입
	@Autowired
	ChatService chatservice;
	
	// boardService에 의존성 주입
	@Autowired
	BoardService boardservice;


	// 메인 페이지
	@GetMapping("/")
	public String home(Model model, @AuthenticationPrincipal UserDetails user, ChatRoom chatRoom) {
		
		// 로그인 했을 때
		if (user != null) {
			
			// member 값을 member에 저장
			// member 값을 받을 것을 model에 전달
			Member member = service.selectUser(user.getUsername());
			model.addAttribute("member", member);
		}
		
		ArrayList<BookBoard> bookList = bookboardservice.bestBoardList();
		ArrayList<Board> board = boardservice.bestBoardList();
		model.addAttribute("reviewList", board);
		model.addAttribute("bookList", bookList);
		return "home";
	}

	// 내 주변 페이지
	@GetMapping("findBoard")
	public String hometest(Model model, @AuthenticationPrincipal UserDetails user) {
		
		// 사용자가 로그인 했을 때
		if (user != null) {
			
			// member 값을 member에 저장
			// member 값을 받을 것을 model에 전달
			Member member = service.selectUser(user.getUsername());
			model.addAttribute("member", member);
		}
		
		// 계시판 전체 조회
		ArrayList<BookBoard> boardList = bookboardservice.showBoardAll();

		// 지도에 계시판을 출력하기 위해 json형식으로 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		String boardListJson = "[]"; // 기본값으로 빈 배열을 설정
			
		// 예외 처리
		try {
			// 자바스크립트 형태를 전달
			boardListJson = objectMapper.writeValueAsString(boardList);
		} catch (JsonProcessingException e) {
			log.error("Error converting boardList to JSON", e);
		}
		
		// boardListJson 형태를 findBoard html에 전달
		// findBoard에 boardList를 전달
		model.addAttribute("boardListJson", boardListJson);
		model.addAttribute("boardlist", boardList);
		return "findBoard";
	}

}
