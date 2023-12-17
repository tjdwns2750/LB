package com.team2.lb.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.BoardDAO;
import com.team2.lb.vo.Board;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardDAO dao;

	@Override
	public ArrayList<Board> showBoardList() {
		ArrayList<Board> boardList = dao.showBoardList();
		return boardList;
	}

}
