package com.team2.lb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.team2.lb.service.ChatService;
import com.team2.lb.vo.ChatMessage;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {
	
	@Autowired
	ChatService service;
	
	 private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

	    //Client가 SEND할 수 있는 경로
	    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
	    //"/pub/chat/enter"
	    @MessageMapping(value = "/chat/enter")
	    public void enter(ChatMessage message){
	        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
	        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	    }

	    @MessageMapping(value = "/chat/message")
	    public void message(ChatMessage message){
	        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	        
	     // 예시: 메시지 저장
	        
	        service.saveMessage(message);
	    }

}
