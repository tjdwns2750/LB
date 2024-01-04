package com.team2.lb.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.team2.lb.vo.Board;
import com.team2.lb.vo.LikeBoard;

@Mapper
public interface BoardDAO {

	ArrayList<Board> showBoardList();

	int writeBoard(Board board);

	ArrayList<Board> selectList(HashMap<String, String> map);

	int countTotal(HashMap<String, String> map);

	void updateHits(int bno);

	Board readBoard(int bno);

	int updateBoard(Board board);

	int deleteBoard(Board board);

	ArrayList<Board> bestBoardList();

	int checkBoardLike(LikeBoard like);

	int selectBoardCnt(LikeBoard like);

	void downBoardLike(LikeBoard like);

	void deleteBoardLike(LikeBoard like);

	void addBoardLike(LikeBoard like);

	void upBoardLike(LikeBoard like);
}
