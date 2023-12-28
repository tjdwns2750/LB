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

import com.team2.lb.service.BookBoardService;
import com.team2.lb.vo.BookBoard;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("bookBoard")
public class BookBoardController {
	
	@Autowired
	BookBoardService service;
	
	@GetMapping("sellBoard")
	public String sellBoard() {
		return "bookBoard/sellBoard";
	}
	
	@PostMapping("registSell")
	public String registSell(BookBoard bookBoard, @AuthenticationPrincipal UserDetails user) {
		log.debug("결과는:{}" ,bookBoard);
		bookBoard.setId(user.getUsername());
		int result = service.registSell(bookBoard);
		return "redirect:/bookBoard/bookBoardList";
	}
	
	@GetMapping("bookBoardList")
	public String boardList(Model model) {
		ArrayList<BookBoard> boardList = service.showBoardList();
		model.addAttribute("boardList", boardList);
		return "bookBoard/bookBoardList";
	}
	
	@GetMapping("read")
	public String read(@RequestParam(name = "boardnum", defaultValue = "0") int boardnum, Model model) {
		log.debug("보드넘:{}", boardnum);
		BookBoard bookBoard = service.readBoard(boardnum);
		model.addAttribute("board",bookBoard);
		return "bookBoard/detailBoard";
	}
	
	@GetMapping("delete")
	public String delete(int boardnum) {
		int result = service.deleteBoard(boardnum);
		return "redirect:/bookBoard/bookBoardList";
	}
	
	@GetMapping("update")
	public String update(int boardnum, Model model) {
		BookBoard bookBoard = service.readBoard(boardnum);
		model.addAttribute("board", bookBoard);
		return "/bookBoard/update";
	}
	
	@PostMapping("update")
	public String update(BookBoard bookBoard, @AuthenticationPrincipal UserDetails user) {
		bookBoard.setId(user.getUsername());
		int result = service.updateBoard(bookBoard);
		return "redirect:/bookBoard/read?boardnum=" + bookBoard.getBbno();
	}

}
