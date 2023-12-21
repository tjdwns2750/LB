package com.team2.lb.service;

import java.util.ArrayList;

import com.team2.lb.vo.BookBoard;

public interface BookBoardService {

	int registSell(BookBoard bookBoard);

	ArrayList<BookBoard> showBoardList();

}
