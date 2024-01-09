package com.team2.lb.service;

import java.util.ArrayList;
import java.util.Map;

import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.BookBoard;
import com.team2.lb.vo.TradeBoard;

public interface TradeBoardService {
	
	// 글 등록
	int registTrade(TradeBoard tradeBoard);

	PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);

	// 보드리스트
	ArrayList<TradeBoard> showBoardList(PageNavigator navi, String type, String searchWord);

	ArrayList<TradeBoard> search(Map<String, String> map);
	
	// 글 읽기
	TradeBoard readBoard(int boardnum);

	int checkLike(int boardnum, String id);

	// 글 업데이트
	int updateBoard(TradeBoard tradeBoard);

	// 글 삭제
	int deleteBoard(int boardnum);

	ArrayList<TradeBoard> showBoardAll();
	
	void addLike(int boardnum, String id);

	void upLike(int boardnum);

	int selectCnt(int boardnum);

	void deleteLike(int boardnum, String id);

	void downLike(int boardnum);
	
	ArrayList<TradeBoard> bestBoardList();

}
