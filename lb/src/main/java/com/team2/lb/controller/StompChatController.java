package com.team2.lb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.team2.lb.service.AlarmService;
import com.team2.lb.service.ChatService;
import com.team2.lb.vo.Alarm;
import com.team2.lb.vo.ChatMessage;
import com.team2.lb.vo.ChatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompChatController {
	
	@Autowired
	ChatService chatService;
	@Autowired
	AlarmService alarmService;
	
	 private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

	    //Client가 SEND할 수 있는 경로
	    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
	    //"/pub/chat/enter"
	 

	    @MessageMapping(value = "/chat/message")
	    public void message(ChatMessage message, Alarm alarm, ChatRoom chatroom){
	    	
	    	
	    	
	    	log.info("alram : {}", alarm);

	        // 예시: 메시지 저장
	    	chatService.saveMessage(message);
	    	
	    	String boardId = chatService.findByBoardId(message.getRoomId()); 
	    	
	    	String Id = chatService.findByMemberId(message.getRoomId());
	    	
	    	int bbno = chatService.findByBbno(message.getRoomId());
	    	
	    	if(Id.equals(message.getWriter())) {
	    		alarm.setMember_id(boardId);
	    	}else {
	    		alarm.setMember_id(Id);
	    	}
	    	

	    	
	    	alarm.setCode("newChat");
	    	
	    	alarm.setPrefix("chat");
	    	log.info("bbno : {}" ,message.getBbno());
	    	alarm.setBbno(bbno);
	    	alarm.setRoom_id(message.getRoomId());
	    	
	    	log.info("alarm : {}", alarm);

	    	alarmService.createChatAlarm(alarm);
	    	
	    	ArrayList<Alarm> alarms = alarmService.showAlarm(alarm.getMember_id());
	    	
	    	template.convertAndSend("/sub/layout/main/" + alarm.getMember_id(), alarms);

	    	
	        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	        
	    }
	    
	    @MessageMapping(value = "/alarm/showAlarm")
	    public void newChat(String id){
	    	
	        ArrayList<Alarm> alarm = alarmService.showAlarm(id);
	        
	        log.info("alarm : {}",alarm);
	        template.convertAndSend("/sub/layout/main/" + id, alarm);


	    }
	    
	    @MessageMapping(value = "/alarm/updateCheck")
	    public void changeCheck(String id){
	    	
	        alarmService.updateCheck(id);

	    }
	    
	    @MessageMapping(value ="/alarm/alarmNum")
	    public void alarmNum(String id) {
	    	int num = alarmService.alarmNum(id);
	    	log.info("num : {}", num);
	    	template.convertAndSend("/sub/layout/main/num" + id, num);
	    }

}
