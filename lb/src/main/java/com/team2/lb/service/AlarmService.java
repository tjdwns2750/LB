package com.team2.lb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.team2.lb.vo.Alarm;

@Service
public interface AlarmService {

	void createChatAlarm(Alarm alarm);

	ArrayList<Alarm> showAlarm(String id);

	void updateCheck(String id);

	int alarmNum(String id);


}
