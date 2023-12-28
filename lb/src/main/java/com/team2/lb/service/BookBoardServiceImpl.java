package com.team2.lb.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.BookBoardDAO;
import com.team2.lb.vo.BookBoard;

@Service
public class BookBoardServiceImpl implements BookBoardService {

	@Autowired
	BookBoardDAO dao;
	
	@Override
	public int registSell(BookBoard bookBoard) {
		int reuslt = dao.registSell(bookBoard);
		return reuslt;
	}

	@Override
	public ArrayList<BookBoard> showBoardList() {
		ArrayList<BookBoard> boardList = dao.showBoardList();
		return boardList;
	}

	@Override
	public BookBoard readBoard(int boardnum) {
		BookBoard bookBoard = dao.readBoard(boardnum);
		return bookBoard;
	}

	@Override
	public int deleteBoard(int boardnum) {
		int result = dao.deleteBoard(boardnum);
		return result;
	}

	@Override
	public int updateBoard(BookBoard bookBoard) {
		int result = dao.updateBoard(bookBoard);
		return result;
	}
	
	
	

	
}
