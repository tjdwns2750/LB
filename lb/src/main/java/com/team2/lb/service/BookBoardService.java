package com.team2.lb.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.BookBoard;
import com.team2.lb.vo.LikeBoard;

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

	int checkLike(LikeBoard like);

	// like 테이블에 정보 추가
	void addLike(LikeBoard like);

	// bookBoard 테이블에 recommend +1
	void upLike(LikeBoard like);
	
	// like 테이블에 정보 삭제
	void deleteLike(LikeBoard like);

	// bookBoard 테이블에 recommend -1
	void downLike(LikeBoard like);

	// 추천수 조회
	int selectCnt(LikeBoard like);


	ArrayList<BookBoard> myShop(String id);

	ArrayList<BookBoard> bestBoardList();

	void sellComplete(int boardnum);

	void resellComplete(int boardnum);

}
