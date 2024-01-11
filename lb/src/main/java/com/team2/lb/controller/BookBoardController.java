package com.team2.lb.controller;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
import com.team2.lb.service.MemberService;
import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.BookBoard;
import com.team2.lb.vo.LikeBoard;
import com.team2.lb.vo.Member;

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

	@Autowired
	MemberService memberService;

	@GetMapping("pay")
	public String pay() {
		return "bookBoard/pay2";
	}

	@GetMapping("sellBoard")
	public String sellBoard() {
		return "bookBoard/sellBoard";
	}

	@ResponseBody
	@GetMapping("sold")
	public String sell(@RequestParam(name = "boardnum", defaultValue = "0") int boardnum) {
		log.debug("보드넘:{}", boardnum);
		service.sellComplete(boardnum);
		return "redirect:/bookBoard/myShop";

	}

	@GetMapping("sale")
	public String resell(@RequestParam(name = "boardnum", defaultValue = "0") int boardnum) {
		service.resellComplete(boardnum);

		return "redirect:/bookBoard/myShop";

	}

	@PostMapping("registSell")
	public String registSell(BookBoard bookBoard, @AuthenticationPrincipal UserDetails user) {
		bookBoard.setId(user.getUsername());
		int result = service.registSell(bookBoard);
		return "redirect:/bookBoard/bookBoardList";
	}

	@GetMapping("myShop")
	public String myShop(Model model, @AuthenticationPrincipal UserDetails user) {
		
		String id = user.getUsername();
		Member member = memberService.selectUser(id);
		log.debug("id는:{}", id);
		ArrayList<BookBoard> boardList = service.myShop(id);
		model.addAttribute("member", member);
		model.addAttribute("boardList", boardList);

		return "bookBoard/myShop";
	}

	@GetMapping("bookBoardList")
	public String boardList(Model model, @RequestParam(name = "page", defaultValue = "1") int page, String type,
			String searchWord) {

		PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);

		ArrayList<BookBoard> boardList = service.showBoardList(navi, type, searchWord);

		model.addAttribute("navi", navi);
		model.addAttribute("boardList", boardList);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);

		return "bookBoard/bookBoardList";
	}

	@GetMapping("read")
	public String read(@RequestParam(name = "boardnum", defaultValue = "0") int boardnum, Model model,
			@AuthenticationPrincipal UserDetails user, LikeBoard likes) {
		BookBoard bookBoard = service.readBoard(boardnum);

//		LocalDate now = LocalDate.now(); 
		/**
		 * // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자) int year = now.getYear();
		 * String month = now.getMonth().toString(); int monthValue =
		 * now.getMonthValue(); int dayOfMonth = now.getDayOfMonth(); int dayOfYear =
		 * now.getDayOfYear(); String dayOfWeek = now.getDayOfWeek().toString(); int
		 * dayOfWeekValue = now.getDayOfWeek().getValue(); // 결과 출력
		 * System.out.println(now); // 2021-06-17 System.out.println(year); // 2021
		 * System.out.println(month + "(" + monthValue + ")"); // JUNE(6)
		 * System.out.println(dayOfMonth); // 17 System.out.println(dayOfYear); // 168
		 * System.out.println(dayOfWeek + "(" + dayOfWeekValue + ")"); // THURSDAY(4) }}
		 * 
		 */
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
//		Date date = formatter.parse(bookBoard.getCreated_day());
		LocalTime now = LocalTime.now(); // 현재시간 출력
//	    System.out.println(now);  // 06:20:57.008731300         // 포맷 정의하기        
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초"); // 포맷 적용하기
//	    String formatedNow = now.format(formatter);         // 포맷 적용된 현재 시간 출력        
//	    System.out.println(formatedNow);  // 06시 20분 57초

		String dateStr = bookBoard.getCreated_day();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");

		log.debug("현재날짜:{}", now);
		log.debug("날짜:{}", dateStr);
//		log.debug("변환한 날짜:{}", boardDate);
		model.addAttribute("board", bookBoard);
		if (user != null) {
			likes.setBno(boardnum);
			likes.setId(user.getUsername());
			likes.setPrefix("sell");
			int check = service.checkLike(likes);
			model.addAttribute("check", check);
			log.info("check {}", check);
		}

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
	public String delete(@RequestParam(name = "boardnum", defaultValue = "0") int boardnum) {
		int result = service.deleteBoard(boardnum);
		return "redirect:/bookBoard/myShop";
	}

	@ResponseBody
	@PostMapping("recommend")
	public int recommend(int boardnum, @AuthenticationPrincipal UserDetails user, LikeBoard likes) {
		// 현재 로그인한 유저의 id를 세팅
		String id = user.getUsername();

		likes.setBno(boardnum);
		likes.setId(id);
		likes.setPrefix("sell");

//		좋아요 테이블에 게시글번호, 유저아이디가 동시에 매칭되는 열이 있는지 체크 있으면 1 없음면 0
		int check = service.checkLike(likes);
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
		String id = user.getUsername();

		likes.setBno(boardnum);
		likes.setId(id);
		likes.setPrefix("sell");

//		좋아요 테이블에 게시글번호, 유저아이디가 동시에 매칭되는 열이 있는지 체크 있으면 1 없음면 0
		int check = service.checkLike(likes);

		return check;

	}

}
