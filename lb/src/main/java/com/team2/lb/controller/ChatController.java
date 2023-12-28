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
@RequestMapping("chat")
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
	@GetMapping("showChatRoom")
	public String showChatRoom(@AuthenticationPrincipal UserDetails user, Model model) {

		ArrayList<ChatRoom> chatRoom = service.showChatRoomAll(user.getUsername());

		if (chatRoom != null) {
			model.addAttribute("roomList", chatRoom);
			log.info("roomList : {}", chatRoom);
			model.addAttribute("room", chatRoom.get(0));
			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatRoom.get(0));

			if (chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}

			log.info("로그 확인 {}", chatmessage);

			return "chat/room";

		}

		return "chat/room";

	}

	@PostMapping("chatRoom")
	public String rooms(Model model, ChatRoom chatRoom, @AuthenticationPrincipal UserDetails user, int bbno,
			String boardId) {
		
		ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());
		
		model.addAttribute("roomList", chatRoomList);
		
		chatRoom.setId(user.getUsername());
		chatRoom.setBoard_id(boardId);
		chatRoom.setBbno(bbno);
		log.info("# All Chat Rooms");

		log.info("chatRoom :  {}", chatRoom);

		int chatRoomNum = service.selectChatRoom(chatRoom);

		if (chatRoom.getBoard_id().equals(user.getUsername())) {
			ArrayList<ChatRoom> chatRoomByBoard = service.showChatRoom(bbno);
			model.addAttribute("room", chatRoomByBoard.get(0));
			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatRoomByBoard.get(0));

			if (chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}

			log.info("로그 확인 {}", chatmessage);

			return "chat/room";

		} else {
			if (chatRoomNum == 0) {

				service.createChatRoom(chatRoom);
			}

			ChatRoom chatrooms = service.findRoomById(chatRoom);

			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatrooms);

			if (chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}

			log.info("로그 확인 {}", chatmessage);

			return "chat/room";
		}
	}

	@PostMapping("BoardchatRoom")
	public String rooms(Model model, ChatRoom chatRoom, @AuthenticationPrincipal UserDetails user, int roomId,
			int bbno) {

		ArrayList<ChatRoom> chatRoomByBoard = service.showChatRoom(bbno);
		ChatRoom selectRoom = service.selectByChatRoom(roomId);
		model.addAttribute("roomList", chatRoomByBoard);
		log.info("roomList : {}", chatRoomByBoard);
		model.addAttribute("room", selectRoom);
		ArrayList<ChatMessage> chatmessage = service.findByMessage(selectRoom);

		if (chatmessage != null) {
			model.addAttribute("chatMessage", chatmessage);
		}

		log.info("로그 확인 {}", chatmessage);

		return "chat/room";
	}

	/*
	 * // 채팅방 개설
	 * 
	 * @PostMapping("/createRoom") public String create(@RequestParam String name) {
	 * 
	 * log.info("# Create Chat Room , name: " + name); return
	 * "redirect:/chat/rooms"; }
	 */

	// 채팅방 조회
	/*
	 * @GetMapping("/rooms") public String getRoom(String roomId, Model model) {
	 * 
	 * log.info("# get Chat Room, roomID : " + roomId);
	 * 
	 * // model.addAttribute("room", service.findRoomById(roomId));
	 * 
	 * // ArrayList<ChatMessage> chatmessage = service.findByMessage(roomId);
	 * 
	 * // model.addAttribute("chatMessage", chatmessage);
	 * 
	 * // log.info("로그 확인 {}", chatmessage);
	 * 
	 * return "chat/room"; }
	 */
}
