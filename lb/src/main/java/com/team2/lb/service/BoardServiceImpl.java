package com.team2.lb.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.BoardDAO;
import com.team2.lb.vo.Board;
import com.team2.lb.vo.LikeBoard;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardDAO dao;
	
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

	@Override
	public int updateBoard(Board board) {
		int result = dao.updateBoard(board);
		return result;
	}

	@Override
	public int deleteBoard(Board board) {
		int result = dao.deleteBoard(board);
		return result;
	}
	
	   @Override
	   public ArrayList<Board> bestBoardList() {
	      ArrayList<Board> board = dao.bestBoardList();
	      return board;
	   }

	@Override
	public int checkBoardLike(LikeBoard like) {
		
		int likes = dao.checkBoardLike(like);
		log.info("check service : {}" , likes);
		return likes;
	}

	@Override
	public int selectBoardCnt(LikeBoard like) {
		int likes = dao.selectBoardCnt(like);
		return likes;
	}

	@Override
	public void downBoardLike(LikeBoard like) {
		dao.downBoardLike(like);
		
	}

	@Override
	public void deleteBoardLike(LikeBoard like) {
		dao.deleteBoardLike(like);
		
	}

	@Override
	public void addBoardLike(LikeBoard like) {
		dao.addBoardLike(like);
		
	}

	@Override
	public void upBoardLike(LikeBoard like) {
		dao.upBoardLike(like);
		
	}
	

}
