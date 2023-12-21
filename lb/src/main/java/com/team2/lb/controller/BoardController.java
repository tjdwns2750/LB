package com.team2.lb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team2.lb.service.BoardService;
import com.team2.lb.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	BoardService service;
	
	@GetMapping("boardList")
	public String boardList(Model model
			, String type
			, String searchWord) {
		
		ArrayList<Board> boardList = service.selectList(type, searchWord);

		model.addAttribute("boardList", boardList);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
	
		return "board/boardList";
	}
	
	@GetMapping("writeForm")
	public String writeForm() {
		return "board/writeForm";
	}
	
	@PostMapping("write")
	public String writeForm(Board board,
			@AuthenticationPrincipal UserDetails user) {
		
		log.debug("write_board: {}", board);
		
		board.setId(user.getUsername());
		
		int result = service.writeBoard(board);
		
		return "redirect:/board/boardList";
	}
	
	@GetMapping("read")
	public String read(@RequestParam(name = "bno", 
				defaultValue = "0")int bno, Model model) {
		//DB에서 글번호에 일치하는 글정보 가져오기
		Board board = service.readBoard(bno);
		log.debug("board??: {}", board);
		// 리플 목록 가져오기
		//ArrayList<Reply> replyList = rService.replyList(bno);
		
		// model 객체를 이용해 readForm.html에 출력
		model.addAttribute("board", board);
		//model.addAttribute("replyList", replyList);
		return "board/readForm";
			
	}
	

	
}
