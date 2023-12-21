package com.team2.lb.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.ChatDAO;
import com.team2.lb.vo.ChatMessage;
import com.team2.lb.vo.ChatRoom;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	ChatDAO dao;
	
	
	private Map<String, ChatRoom> chatRoomMap;
	
	@PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
    }

	@Override
	public void createChatRoom(String name) {
		ChatRoom room = dao.createChatRoom(name);
        chatRoomMap.put(room.getRoomId(), room);
	}

	
	@Override
	public ChatRoom findRoomById(String roomId) {
		ChatRoom result = dao.findRoomById(roomId);
		return result;
		//return ChatRoom.get(roomId);
	}
	

	@Override
	public List<ChatRoom> findAllRooms() {
		List<ChatRoom> result = dao.findAllRooms();
		Collections.reverse(result);
		return result;
	}

	@Override
	public void saveMessage(ChatMessage message) {
		dao.saveMessage(message);
		
	}

	@Override
	public ArrayList<ChatMessage> findByMessage(String roomId) {
		ArrayList<ChatMessage> chatMessage = dao.findByMessage(roomId);
		return chatMessage;
	}

}
