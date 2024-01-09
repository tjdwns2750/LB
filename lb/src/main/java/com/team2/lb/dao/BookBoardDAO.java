package com.team2.lb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.team2.lb.vo.BookBoard;

@Mapper
public interface BookBoardDAO {

	int registSell(BookBoard bookBoard);

	ArrayList<BookBoard> showBoardList(HashMap<String, String> map, RowBounds rb);

	ArrayList<BookBoard> search(Map<String, String> map);

	int countTotal(HashMap<String, String> map);

	BookBoard readBoard(int boardnum);

	void updateHits(int boardnum);

	int updateBoard(BookBoard bookBoard);

	int deleteBoard(int boardnum);

	ArrayList<BookBoard> showBoardAll();

	int checkLike(HashMap<String, Object> map);
	
	void addLike(HashMap<String, Object> map);

	void upLike(int boardnum);

	void deleteLike(HashMap<String, Object> map);

	void downLike(int boardnum);

	int selectCnt(int boardnum);

	ArrayList<BookBoard> myShop(String id);

	ArrayList<BookBoard> bestBoardList();


	
}
