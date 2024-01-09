package com.team2.lb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.team2.lb.vo.LikeBoard;
import com.team2.lb.vo.TradeBoard;

@Mapper
public interface TradeBoardDAO {

	int registTrade(TradeBoard bookBoard);

	int countTotal(HashMap<String, String> map);

	ArrayList<TradeBoard> showBoardList(HashMap<String, String> map, RowBounds rb);

	void updateHits(int boardnum);

	TradeBoard readBoard(int boardnum);

	int checkLike(LikeBoard likes);

	int updateBoard(TradeBoard tradeBoard);

	int deleteBoard(int boardnum);

	void addLike(LikeBoard likes);

	void upLike(LikeBoard likes);

	void deleteLike(LikeBoard likes);

	void downLike(LikeBoard likes);

	int selectCnt(LikeBoard likes);

	ArrayList<TradeBoard> bestBoardList();

	ArrayList<TradeBoard> showBoardAll();

	ArrayList<TradeBoard> search(Map<String, String> map);

}
