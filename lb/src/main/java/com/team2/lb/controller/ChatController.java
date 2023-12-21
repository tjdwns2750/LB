package com.team2.lb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team2.lb.service.ChatService;
import com.team2.lb.vo.ChatMessage;
import com.team2.lb.vo.ChatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	ChatService service;

	/*
	 * @GetMapping("/") public String chatGET(){
	 * 
	 * log.info("@ChatController, chat GET()");
	 * 
	 * return "jinu/chat"; }
	 */

	// 채팅방 목록 조회
	@GetMapping("/chatRoom")
	public String rooms(Model model, ChatRoom chatRoom, @AuthenticationPrincipal UserDetails user, String bbno, String id) {
		chatRoom.setId(user.getUsername());
		chatRoom.setBuno(bbno);
		chatRoom.setBuno(id);
		log.info("# All Chat Rooms");
		
		log.info("chatRoom :  {}", chatRoom);
		
		int chatRoomNum = service.selectChatRoom(chatRoom);
		
		if(chatRoomNum == 0) {
			
			service.createChatRoom(chatRoom);
		}

		model.addAttribute("room", service.findRoomById(chatRoom));
		
		/*
		
		ChatRoom chatrooms = service.findRoomById(chatRoom);
		
		int countMsg =  service.countMessage();
		
		if(service.countMessage() != null) {
			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatrooms.getRoomId());
			
			model.addAttribute("chatMessage", chatmessage);
			
			log.info("로그 확인 {}", chatmessage);
		}
		*/

		return "chat/room";
	}

	// 채팅방 개설
	@PostMapping("/createRoom")
	public String create(@RequestParam String name) {

		log.info("# Create Chat Room , name: " + name);
		return "redirect:/chat/rooms";
	}

	// 채팅방 조회
	@GetMapping("/rooms")
	public String getRoom(String roomId, Model model) {

		log.info("# get Chat Room, roomID : " + roomId);

		// model.addAttribute("room", service.findRoomById(roomId));

		// ArrayList<ChatMessage> chatmessage = service.findByMessage(roomId);

		// model.addAttribute("chatMessage", chatmessage);

		// log.info("로그 확인 {}", chatmessage);

		return "chat/room";
	}

}
