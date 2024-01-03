package com.team2.lb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team2.lb.service.BookBoardService;
import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.BookBoard;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("bookBoard")
public class BookBoardController {
	

	@Value("${user.board.page}")
	int countPerPage;
	
	@Value("${user.board.group}")
	int pagePerGroup;
	
	@Autowired
	BookBoardService service;
	
	@GetMapping("pay")
	public String pay() {
		return "bookBoard/pay2";
	}
	
	@GetMapping("sellBoard")
	public String sellBoard() {
		return "bookBoard/sellBoard";
	}
	
	@PostMapping("registSell")
	public String registSell(BookBoard bookBoard, @AuthenticationPrincipal UserDetails user) {
		bookBoard.setId(user.getUsername());
		int result = service.registSell(bookBoard);
		return "redirect:/bookBoard/bookBoardList";
	}
	
	@GetMapping("bookBoardList")
	public String boardList(Model model
								,@RequestParam(name = "page", defaultValue = "1") int page
								, String type, String searchWord
								) {
		
		PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);
		
		ArrayList<BookBoard> boardList = service.showBoardList(navi, type, searchWord);
		
		model.addAttribute("navi", navi);
		model.addAttribute("boardList", boardList);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
		
		return "bookBoard/bookBoardList";
	}
	
	@GetMapping("read")
	public String read(@RequestParam(name = "boardnum", defaultValue = "0") int boardnum, Model model) {
		BookBoard bookBoard = service.readBoard(boardnum);
		model.addAttribute("board",bookBoard);
		return "bookBoard/detailBoard";
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

	@GetMapping("delete")
	public String delete(int boardnum) {
		int result = service.deleteBoard(boardnum);
		return "redirect:/bookBoard/bookBoardList";
	}
	
	@ResponseBody
	@PostMapping("recommend")
	public int recommend(int boardnum, @AuthenticationPrincipal UserDetails user) {
		// 현재 로그인한 유저의 id를 세팅
		String id = user.getUsername();	
		
//		좋아요 테이블에 게시글번호, 유저아이디가 동시에 매칭되는 열이 있는지 체크 있으면 1 없음면 0
		int check = service.checkLike(boardnum, id);
//		좋아요 수를 담을 cnt
		int cnt = 0;
		
		if (check == 0) {
			log.debug("추천안했음");
			service.addLike(boardnum, id);
			service.upLike(boardnum);
			cnt = service.selectCnt(boardnum);
			return cnt;
		} else {
			log.debug("추천이미했음");
			service.deleteLike(boardnum, id);
			service.downLike(boardnum);
			cnt = service.selectCnt(boardnum);
			return cnt;
		}
		
	}
}
