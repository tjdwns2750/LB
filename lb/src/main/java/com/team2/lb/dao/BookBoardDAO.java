package com.team2.lb.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.team2.lb.vo.BookBoard;

@Mapper
public interface BookBoardDAO {

	int registSell(BookBoard bookBoard);

	ArrayList<BookBoard> showBoardList();

}
