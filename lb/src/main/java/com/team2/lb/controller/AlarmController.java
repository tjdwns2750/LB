package com.team2.lb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team2.lb.service.AlarmService;
import com.team2.lb.vo.Alarm;

@Controller
@RequestMapping("alarm")
public class AlarmController {
	
	@Autowired
	AlarmService service;
	
//	@PostMapping("showAlarm")
//	public ArrayList<Alarm> showAlarm(Model model ,String id) {
//		ArrayList<Alarm> alarm = service.showAlarm(id);
//		model.addAttribute("alarm", alarm);
//		return alarm;
//	}
}
