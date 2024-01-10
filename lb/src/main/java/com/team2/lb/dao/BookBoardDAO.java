package com.team2.lb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.team2.lb.vo.BookBoard;
import com.team2.lb.vo.LikeBoard;

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

	int checkLike(LikeBoard likes);
	
	void addLike(LikeBoard likes);

	void upLike(LikeBoard likes);

	void deleteLike(LikeBoard likes);

	void downLike(LikeBoard likes);

	int selectCnt(LikeBoard likes);

	ArrayList<BookBoard> myShop(String id);

	ArrayList<BookBoard> bestBoardList();

	void sellComplete(int boardnum);

	void resellComplete(int boardnum);


	
}
