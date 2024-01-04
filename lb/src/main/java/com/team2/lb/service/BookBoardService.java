package com.team2.lb.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.BookBoard;

public interface BookBoardService {

	// 글 등록
	int registSell(BookBoard bookBoard);

	// 보드리스트
	ArrayList<BookBoard> showBoardList(PageNavigator navi, String type, String searchWord);

	ArrayList<BookBoard> search(Map<String, String> map);
	
	PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);

	// 글 읽기
	BookBoard readBoard(int boardnum);

	// 글 업데이트
	int updateBoard(BookBoard bookBoard);

	// 글 삭제
	int deleteBoard(int boardnum);

	ArrayList<BookBoard> showBoardAll();

	int checkLike(int boardnum, String id);

	// like 테이블에 정보 추가
	void addLike(int boardnum, String id);

	// bookBoard 테이블에 recommend +1
	void upLike(int boardnum);
	
	// like 테이블에 정보 삭제
	void deleteLike(int boardnum, String id);

	// bookBoard 테이블에 recommend -1
	void downLike(int boardnum);

	// 추천수 조회
	int selectCnt(int boardnum);

	ArrayList<BookBoard> bestBoardList();
	

}
