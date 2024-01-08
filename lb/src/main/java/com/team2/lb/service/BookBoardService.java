package com.team2.lb.service;

import java.util.ArrayList;
import java.util.Map;

import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.BookBoard;

public interface BookBoardService {

	int registSell(BookBoard bookBoard);

	ArrayList<BookBoard> showBoardList(PageNavigator navi, String type, String searchWord);

	ArrayList<BookBoard> search(Map<String, String> map);
	
	PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);

	BookBoard readBoard(int boardnum);

	int updateBoard(BookBoard bookBoard);

	int deleteBoard(int boardnum);

	ArrayList<BookBoard> showBoardAll();

}
