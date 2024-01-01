package com.team2.lb.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.team2.lb.vo.Alarm;

@Mapper
public interface AlarmDAO {

	void createAlarm(Alarm alarm);

	ArrayList<Alarm> showAlarm(String roomId);

	void updateAlarm(String id);

	int alarmNum(String id);

}
