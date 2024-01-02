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

	int updateBoard(BookBoard bookBoard);

	int deleteBoard(int boardnum);
}
