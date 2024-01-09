package com.team2.lb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.team2.lb.vo.TradeBoard;

@Mapper
public interface TradeBoardDAO {

	int registTrade(TradeBoard bookBoard);

	int countTotal(HashMap<String, String> map);

	ArrayList<TradeBoard> showBoardList(HashMap<String, String> map, RowBounds rb);

	void updateHits(int boardnum);

	TradeBoard readBoard(int boardnum);

	int checkLike(HashMap<String, Object> map);

	int updateBoard(TradeBoard tradeBoard);

	int deleteBoard(int boardnum);

	void addLike(HashMap<String, Object> map);

	void upLike(int boardnum);

	void deleteLike(HashMap<String, Object> map);

	void downLike(int boardnum);

	int selectCnt(int boardnum);

	ArrayList<TradeBoard> bestBoardList();

	ArrayList<TradeBoard> showBoardAll();

	ArrayList<TradeBoard> search(Map<String, String> map);

}
