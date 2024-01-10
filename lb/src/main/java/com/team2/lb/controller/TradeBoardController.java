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

import com.team2.lb.service.TradeBoardService;
import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.LikeBoard;
import com.team2.lb.vo.TradeBoard;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("tradeBoard")
public class TradeBoardController {
	
	@Value("${user.board.page}")
	int countPerPage;

	@Value("${user.board.group}")
	int pagePerGroup;

	@Autowired
	TradeBoardService service;

	@GetMapping("tradeBoard")
	public String tradeBoard() {
		return "tradeBoard/tradeBoard";
	}

	@PostMapping("registTrade")
	public String registTrade(TradeBoard tradeBoard, @AuthenticationPrincipal UserDetails user) {
		tradeBoard.setId(user.getUsername());
		int result = service.registTrade(tradeBoard);
		return "redirect:/tradeBoard/tradeBoardList";
	}
	
	@GetMapping("tradeBoardList")
	public String boardList(Model model, @RequestParam(name = "page", defaultValue = "1") int page, String type,
			String searchWord) {
		
		PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);

		ArrayList<TradeBoard> boardList = service.showBoardList(navi, type, searchWord);

		model.addAttribute("navi", navi);
		model.addAttribute("boardList", boardList);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
		
		return "tradeboard/tradeBoardList";
	}
	
	@GetMapping("read")
	public String read(@RequestParam(name = "boardnum", defaultValue = "0") int boardnum, Model model, @AuthenticationPrincipal UserDetails user, LikeBoard likes) {
		TradeBoard tradeBoard = service.readBoard(boardnum);
		model.addAttribute("board", tradeBoard);
		if(user!= null) {
			likes.setBno(boardnum);
			likes.setId(user.getUsername());
			likes.setPrefix("exchange");
			int check = service.checkLike(likes);
			model.addAttribute("check" , check);
			log.info("check {}", check);
		}
		
		return "tradeBoard/detailBoard";
	}

	@GetMapping("update")
	public String update(int boardnum, Model model) {
		TradeBoard tradeBoard = service.readBoard(boardnum);
		int result = service.updateBoard(tradeBoard);
		return "/tradeBoard/update";
		}
	
	@PostMapping("update")
	public String update(TradeBoard tradeBoard, @AuthenticationPrincipal UserDetails user) {
		tradeBoard.setId(user.getUsername());
		int result = service.updateBoard(tradeBoard);
		return "redirect:/tradeBoard/read?boardnum=" + tradeBoard.getBbno();
	}
	
	@GetMapping("delete")
	public String delete(int boardnum) {
		int result = service.deleteBoard(boardnum);
		return "redirect:/tradeBoard/tradeBoardList";
	}
	
	@ResponseBody
	@PostMapping("recommend")
	public int recommend(int boardnum, @AuthenticationPrincipal UserDetails user, LikeBoard likes) {
		// 현재 로그인한 유저의 id를 세팅
		String id = user.getUsername();
		likes.setBno(boardnum);
		likes.setId(id);
		likes.setPrefix("exchange");
		

//		좋아요 테이블에 게시글번호, 유저아이디가 동시에 매칭되는 열이 있는지 체크 있으면 1 없음면 0
		int check = service.checkLike(likes);
		log.info("likes : {}" , likes);
//		좋아요 수를 담을 cnt
		int cnt = 0;

		if (check == 0) {
			log.debug("추천안했음");
			service.addLike(likes);
			service.upLike(likes);
			cnt = service.selectCnt(likes);
			return cnt;
		} else {
			log.debug("추천이미했음");
			service.deleteLike(likes);
			service.downLike(likes);
			cnt = service.selectCnt(likes);
			return cnt;
		}

	}

	@ResponseBody
	@PostMapping("likeCnt")
	public int likeCnt(int boardnum, @AuthenticationPrincipal UserDetails user, LikeBoard likes) {
		// 현재 로그인한 유저의 id를 세팅
		likes.setBno(boardnum);
		likes.setId(user.getUsername());
		likes.setPrefix("exchange");
		
		log.info(" {}" , likes);
//		좋아요 테이블에 게시글번호, 유저아이디가 동시에 매칭되는 열이 있는지 체크 있으면 1 없음면 0
		int check = service.checkLike(likes);

		return check;

	}
	
}
