package com.team2.lb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.lb.vo.ChatMessage;
import com.team2.lb.vo.ChatRoom;

@Service
public interface ChatService {

	void createChatRoom(ChatRoom chatRoom);

	ChatRoom findRoomById(ChatRoom chatRoom);

	List<ChatRoom> findAllRooms();

	void saveMessage(ChatMessage message);

	ArrayList<ChatMessage> findByMessage(ChatRoom chatrooms);

	int selectChatRoom(ChatRoom chatRoom);

	ArrayList<ChatRoom> showChatRoom(int bbno);

	ChatRoom selectByChatRoom(int roomId);


}
