package com.team2.lb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.TradeBoardDAO;
import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.BookBoard;
import com.team2.lb.vo.TradeBoard;

@Service
public class TradeBoardServiceImpl implements TradeBoardService{

	@Autowired
	TradeBoardDAO dao;
	
	@Override
	public int registTrade(TradeBoard tradeBoard) {
		int reuslt = dao.registTrade(tradeBoard);
		return reuslt;
	}
	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
			String searchWord) {
		HashMap<String, String> map = getMap(type, searchWord);

//		검색 키워드가 있든 없든 전체 글수를 DB로 부터 조회하기
		int total = dao.countTotal(map);

		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		return navi;
	}
	
	private HashMap<String, String> getMap(String type, String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		return map;
	}
	
	@Override
	public ArrayList<TradeBoard> showBoardList(PageNavigator navi, String type, String searchWord) {
		HashMap<String, String> map = getMap(type, searchWord);

//		MyBatis 에서 제공해주는 record를 관리하는 객체 RowBounds
//		param 2개 : 1=시작레코드, 2=몇개가져올지
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());

		ArrayList<TradeBoard> boardList = dao.showBoardList(map, rb);
		return boardList;
	}
	@Override
	public TradeBoard readBoard(int boardnum) {
		dao.updateHits(boardnum);
		TradeBoard tradeBoard = dao.readBoard(boardnum);
		return tradeBoard;
	}
	@Override
	public int checkLike(int boardnum, String id) {
		HashMap<String, Object> map = getMap(boardnum, id);
		int check = dao.checkLike(map);
		return check;
	}
	@Override
	public int updateBoard(TradeBoard tradeBoard) {
		int result = dao.updateBoard(tradeBoard);
		return result;
	}
	@Override
	public int deleteBoard(int boardnum) {
		int result = dao.deleteBoard(boardnum);
		return result;
	}
	@Override
	public void addLike(int boardnum, String id) {
		HashMap<String, Object> map = getMap(boardnum, id);
		dao.addLike(map);
		
	}
	private HashMap<String, Object> getMap(int boardnum, String id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("boardnum", boardnum);
		map.put("id" , id);
		return map;
	}
	@Override
	public void upLike(int boardnum) {
		dao.upLike(boardnum);
		
	}
	@Override
	public int selectCnt(int boardnum) {
		int cnt = dao.selectCnt(boardnum);
		return cnt;
	}
	@Override
	public void deleteLike(int boardnum, String id) {
		HashMap<String, Object> map = getMap(boardnum, id);
		dao.deleteLike(map);
		
	}
	@Override
	public void downLike(int boardnum) {
		dao.downLike(boardnum);
		
	}
	@Override
	public ArrayList<TradeBoard> search(Map<String, String> map) {
		ArrayList<TradeBoard> boardList = dao.search(map);
		return boardList;
	}
	@Override
	public ArrayList<TradeBoard> showBoardAll() {
		ArrayList<TradeBoard> boardlist = dao.showBoardAll();
		return boardlist;
	}
	@Override
	public ArrayList<TradeBoard> bestBoardList() {
		ArrayList<TradeBoard> bookList = dao.bestBoardList();
		return bookList;
	}
	
	

}
