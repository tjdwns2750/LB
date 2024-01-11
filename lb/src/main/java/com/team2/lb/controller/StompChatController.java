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
	
	// AlarmService 의존관계도 자동으로 주입
	@Autowired
	ChatService chatService;
	
	// AlarmService 의존관계도 자동으로 주입
	@Autowired
	AlarmService alarmService;
	
	 private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

	    //Client가 SEND할 수 있는 경로
	    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
	 
	 	// 채팅을 전달 받았을 때 메세지를 전달받았을때
	    @MessageMapping(value = "/chat/message")
	    public void message(ChatMessage message, Alarm alarm, ChatRoom chatroom){
	    	
	        // 예시: 메시지 저장
	    	chatService.saveMessage(message);
	    
	    	// message vo에서 alarm vo에 전닳할 값을 찾기
	    	String boardId = chatService.findByBoardId(message.getRoomId()); 
	    	String Id = chatService.findByMemberId(message.getRoomId());
	    	int bbno = chatService.findByBbno(message.getRoomId());
	    	
	    	// 알람이 메시지를 보낸 상대방에 전달되게 구성
	    	if(Id.equals(message.getWriter())) {
	    		alarm.setMember_id(boardId);
	    	}else {
	    		alarm.setMember_id(Id);
	    	}
	    	
	    	// alarm를 vo를 전달해 주기 위해 객체에 field값을 지정
	    	alarm.setCode("newChat");
	    	alarm.setPrefix("chat");
	    	alarm.setBbno(bbno);
	    	alarm.setRoom_id(message.getRoomId());
	    	
	    	// 알람 생성
	    	alarmService.createChatAlarm(alarm);
	    	
	    	// 알람 리스트 찾기
	    	ArrayList<Alarm> alarms = alarmService.showAlarm(alarm.getMember_id());
	    	
	    	// 헤더에 알람 리스트를 전달 데이터를 전달
	    	template.convertAndSend("/sub/layout/main/" + alarm.getMember_id(), alarms);
	    	
	    	// 채팅방에 메시지를 입력한 메시지를 전달
	        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	    }
	    
	    // 알람 보이게 할때
	    @MessageMapping(value = "/alarm/showAlarm")
	    public void newChat(String id){
	    	
	    	// 알람리스트 셍상
	        ArrayList<Alarm> alarm = alarmService.showAlarm(id);
	        
	        //헤더에 알람 리스트를 전달
	        template.convertAndSend("/sub/layout/main/" + id, alarm);
	    }
	    
	    // /alarm/updateCheck를 pubScribe 받았을 때
	    @MessageMapping(value = "/alarm/updateCheck")
	    public void changeCheck(String id){
	    	
	    	// check 부분을 Y로 변경
	        alarmService.updateCheck(id);

	    }
	    
	    // 알람 리스트의 숫자를 보일 때
	    @MessageMapping(value ="/alarm/alarmNum")
	    public void alarmNum(String id) {
	    	
	    	// 알람 수를 전달 받음
	    	int num = alarmService.alarmNum(id);
	    	
	    	// 알람 수를 헤더에 전달
	    	template.convertAndSend("/sub/layout/main/num" + id, num);
	    }

}
