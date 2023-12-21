package com.team2.lb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.team2.lb.vo.Board;

@Service
public interface BoardService {

	ArrayList<Board> showBoardList();

	int writeBoard(Board board);

	ArrayList<Board> selectList(String type, String searchWord);

	Board readBoard(int bno);

}
