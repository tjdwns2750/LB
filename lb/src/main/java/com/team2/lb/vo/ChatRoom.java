package com.team2.lb.vo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
	private int roomId; // 채팅방 아이디
	private String id; // 사용자 아이디
	private String buno; // 블로그 사용자
	private String bbno; // 블로그 번호
	private String created_day; // 생성일
	// private String roomName;
	
}
