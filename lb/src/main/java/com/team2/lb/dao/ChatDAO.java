package com.team2.lb.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.team2.lb.vo.ChatMessage;
import com.team2.lb.vo.ChatRoom;

@Mapper
public interface ChatDAO {

	ChatRoom findRoomById(ChatRoom chatRoom);

	void createChatRoom(ChatRoom chatRoom);

	List<ChatRoom> findAllRooms();

	void saveMessage(ChatMessage message);

	ArrayList<ChatMessage> findByMessage(int roomId);

	int selectChatRoom(ChatRoom chatRoom);

}
