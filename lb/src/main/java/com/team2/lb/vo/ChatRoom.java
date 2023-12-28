package com.team2.lb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
	private long roomId; // 채팅방 아이디
	private String id; // 사용자 아이디
	private String buno; // 블로그 사용자
	private long bbno; // 블로그 번호
	private String created_day; // 생성일
	private String name; // 사용자 이름
	// private String roomName;
	
}
