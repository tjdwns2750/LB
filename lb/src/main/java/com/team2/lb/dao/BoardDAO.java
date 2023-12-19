package com.team2.lb.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.team2.lb.vo.Board;

@Mapper
public interface BoardDAO {

	ArrayList<Board> showBoardList();

}
