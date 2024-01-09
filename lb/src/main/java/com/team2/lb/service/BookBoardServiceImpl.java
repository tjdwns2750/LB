package com.team2.lb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.BookBoardDAO;
import com.team2.lb.util.PageNavigator;
import com.team2.lb.vo.BookBoard;
import com.team2.lb.vo.LikeBoard;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookBoardServiceImpl implements BookBoardService {

	@Autowired
	BookBoardDAO dao;

	@Override
	public int registSell(BookBoard bookBoard) {
		int reuslt = dao.registSell(bookBoard);
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

	@Override
	public ArrayList<BookBoard> showBoardList(PageNavigator navi, String type, String searchWord) {

		HashMap<String, String> map = getMap(type, searchWord);

//		MyBatis 에서 제공해주는 record를 관리하는 객체 RowBounds
//		param 2개 : 1=시작레코드, 2=몇개가져올지
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());

		ArrayList<BookBoard> boardList = dao.showBoardList(map, rb);
		return boardList;
	}

	@Override
	public ArrayList<BookBoard> search(Map<String, String> map) {
		ArrayList<BookBoard> boardList = dao.search(map);
		return boardList;
	}
	
	private HashMap<String, String> getMap(String type, String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		return map;
	}

	@Override
	public BookBoard readBoard(int boardnum) {
		dao.updateHits(boardnum);
		BookBoard bookBoard = dao.readBoard(boardnum);
		return bookBoard;
	}

	@Override
	public int updateBoard(BookBoard bookBoard) {
		int result = dao.updateBoard(bookBoard);
		return result;
	}

	@Override
	public int deleteBoard(int boardnum) {
		int result = dao.deleteBoard(boardnum);
		return result;
	}

	@Override
	public ArrayList<BookBoard> showBoardAll() {
		ArrayList<BookBoard> boardlist = dao.showBoardAll();
		return boardlist;
	}
	
	private HashMap<String, Object> getMap(int boardnum, String id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("boardnum", boardnum);
		map.put("id" , id);
		return map;
	}
	
	@Override
	public int checkLike(LikeBoard likes) {
		log.info("like : {}" , likes);
		int check = dao.checkLike(likes);
		return check;
	}

	@Override
	public void addLike(LikeBoard likes) {
		dao.addLike(likes);
	}
	@Override
	public void upLike(LikeBoard likes) {
		dao.upLike(likes);
	}

	@Override
	public void deleteLike(LikeBoard likes) {
		dao.deleteLike(likes);
	}

	@Override
	public void downLike(LikeBoard likes) {
		dao.downLike(likes);
	}
	
	@Override
	public int selectCnt(LikeBoard likes) {
		int cnt = dao.selectCnt(likes);
		return cnt;
	}

	@Override
	public ArrayList<BookBoard> myShop(String id) {
		ArrayList<BookBoard> boardlist = dao.myShop(id);
		return boardlist;
	}
	
  @Override
	public ArrayList<BookBoard> bestBoardList() {
		 ArrayList<BookBoard> bookList = dao.bestBoardList();
		return bookList;
	}
	
  @Override
  public void sellComplete(int boardnum) {
     dao.sellComplete(boardnum);
     
  } 

}
