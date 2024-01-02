package com.team2.lb.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.AlarmDAO;
import com.team2.lb.vo.Alarm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlarmSercieImpl implements AlarmService {
	
	@Autowired
	AlarmDAO dao;

	@Override
	public void createChatAlarm(Alarm alarm) {
		dao.createAlarm(alarm);
		
	}

	@Override
	public ArrayList<Alarm> showAlarm(String id) {
		ArrayList<Alarm> alarm = dao.showAlarm(id);
		return alarm;
	}

	@Override
	public void updateCheck(String id) {
		dao.updateAlarm(id);
		
	}

	@Override
	public int alarmNum(String id) {
		int num = dao.alarmNum(id);
		return num;
	}

	@Override
	public void createReiviewAlarm(Alarm alarm) {
		dao.createReiviewAlarm(alarm);
		
	}

	@Override
	public String selectBoardId(int bno) {
		String boardId = dao.selectBoardId(bno);
		return boardId;
	}



}
