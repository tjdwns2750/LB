package com.team2.lb.service;

import java.util.ArrayList;
import java.util.HashMap;

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

	@Override
	public int writeBoard(Board board) {
		int result = dao.writeBoard(board);
		return result;
	}

	@Override
	public ArrayList<Board> selectList(String type, String searchWord) {
		HashMap<String, String> map = getMap(type, searchWord);
		
		ArrayList<Board> result = dao.selectList(map);
		int total = dao.countTotal(map);
		return result;
	}

	private HashMap<String, String> getMap(String type, String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		
		map.put("type", type);
		map.put("searchWord", searchWord);
		return map;
	}

	@Override
	public Board readBoard(int bno) {
		//조회수 증가
		dao.updateHits(bno);
		//글정보 가져오기
		Board board = dao.readBoard(bno);
		return board;
	}

}
