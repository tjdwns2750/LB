package com.team2.lb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.team2.lb.vo.Board;
import com.team2.lb.vo.BookBoard;

@Service
public interface BoardService {


	int writeBoard(Board board);

	ArrayList<Board> selectList(String type, String searchWord);

	Board readBoard(int bno);

	int updateBoard(Board board);

	int deleteBoard(Board board);
	
	ArrayList<Board> bestBoardList();

}
