package com.team2.lb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team2.lb.service.ChatService;
import com.team2.lb.vo.ChatMessage;
import com.team2.lb.vo.ChatRoom;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("chat")
public class ChatController {

	@Autowired
	ChatService service;

	// 해더에서 채팅을 누르면 호출하는 url
	@GetMapping("showChatRoom")
	public String showChatRoom(@AuthenticationPrincipal UserDetails user, Model model) {
		
		// 채팅룸 리스트를 chatRooms에 객체로 저장
		ArrayList<ChatRoom> chatRooms = service.showChatRoomAll(user.getUsername());

		// 채팅이 있는 경우
		if (chatRooms != null && !chatRooms.isEmpty()) {
			
			// roomList, room 값을 model에 전달
			model.addAttribute("roomList", chatRooms);
			model.addAttribute("room", chatRooms.get(0));

			// 채팅
			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatRooms.get(0));

			// 채팅 메세지가 있는 경우 채팅 메세지를 모델로 전달
			if (chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}

		// 채팅이 없는 경우 html에 에러를 방지하기 위해 null 값을 전달
		} else {
			chatRooms = null;
			model.addAttribute("roomList", chatRooms);
		}

		return "chat/room";
	}

	// 판매에서 채팅을 눌렀을 때
	@PostMapping("chatRoom")
	public String rooms(Model model, ChatRoom chatRoom, @AuthenticationPrincipal UserDetails user, int bbno,
			String boardId) {

		// chatRoom 데이터베이스에 값을 저장하기 위해 객체에 field값을 저장
		chatRoom.setId(user.getUsername());
		chatRoom.setBoard_id(boardId);
		chatRoom.setBbno(bbno);

		// 채팅방이 존재하는지 판변할기 위해 받은 필드
		int chatRoomNum = service.selectChatRoom(chatRoom);

		// 판매에 올린 작성자일때
		if (chatRoom.getBoard_id().equals(user.getUsername())) {

			//chatRoomList, boardm, chatMessage값을 객체에  저장
			ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());
			ArrayList<ChatRoom> chatRoomByBoard = service.showChatRoom(bbno);
			ArrayList<ChatMessage> chatMessage = service.findByMessage(chatRoomByBoard.get(0));
			
			// 모델에 저장해둔 정보들을 전달
			model.addAttribute("roomList", chatRoomList);
			model.addAttribute("room", chatRoomByBoard.get(0));

			// 채팅 메세지가 있는 경우 채팅 메세지를 모델로 전달
			if (chatMessage != null) {
				model.addAttribute("chatMessage", chatMessage);
			}

			return "chat/room";

		// 판매에 올린 작성자가 아닐 때
		} else {
		
			// 채팅방이 존재하지 않을 경우
			if (chatRoomNum == 0) {

				// 새로운 채팅방을 생성
				service.createChatRoom(chatRoom);
			}

			// 접속한 유저의 채팅 리스트와, 해당 현재 누른 채팅방을 데이터를 가져옴
			ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());
			ChatRoom chatrooms = service.findRoomById(chatRoom);

			// 모델에 채팅 리스트와 해당 현재 누른 채팅방을 전달
			model.addAttribute("roomList", chatRoomList);
			model.addAttribute("room", chatrooms);
			
			//
			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatrooms);
			
			// 채팅 메세지가 있는 경우 채팅 메세지를 모델로 전달
			if (chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}

			return "chat/room";
		}
	}

	@PostMapping("BoardchatRoom")
	public String rooms(Model model, ChatRoom chatRoom, @AuthenticationPrincipal UserDetails user, int roomId,
			int bbno) {

		// 모델에 현재 채팅방리스트와, 채팅방을 전달
		ChatRoom selectRoom = service.selectByChatRoom(roomId);
		ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());
		ArrayList<ChatMessage> chatmessage = service.findByMessage(selectRoom);
		
		// 모델에 현재 채팅방리스트와, 채팅방을 전달
		model.addAttribute("roomList", chatRoomList);
		model.addAttribute("room", selectRoom);
		

		// chatMessage에 값이 있을때
		if (chatmessage != null) {
			
			//모델에 데이터를 전달
			model.addAttribute("chatMessage", chatmessage);
		}

		return "chat/room";
	}
}
