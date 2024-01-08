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

	@GetMapping("showChatRoom")
	public String showChatRoom(@AuthenticationPrincipal UserDetails user, Model model) {
		log.info("user id: {}", user.getUsername());		// 한 번만 호출하도록 수정
		ArrayList<ChatRoom> chatRooms = service.showChatRoomAll(user.getUsername());

		log.info("chatriin {}", chatRooms);

		if (chatRooms != null && !chatRooms.isEmpty()) {
			model.addAttribute("roomList", chatRooms);
			log.info("roomList : {}", chatRooms);
			model.addAttribute("room", chatRooms.get(0));

			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatRooms.get(0));

			if (chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}

			log.info("로그 확인 {}", chatmessage);
		}else {
			chatRooms = null;
			model.addAttribute("roomList", chatRooms);
		}

		return "chat/room";
	}

	@PostMapping("chatRoom")
	public String rooms(Model model, ChatRoom chatRoom, @AuthenticationPrincipal UserDetails user, int bbno,
			String boardId) {


		chatRoom.setId(user.getUsername());
		chatRoom.setBoard_id(boardId);
		chatRoom.setBbno(bbno);
		log.info("# All Chat Rooms");

		log.info("chatRoom :  {}", chatRoom);

		int chatRoomNum = service.selectChatRoom(chatRoom);

		if (chatRoom.getBoard_id().equals(user.getUsername())) {
			
			ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());
			
			model.addAttribute("roomList", chatRoomList);
			
			ArrayList<ChatRoom> chatRoomByBoard = service.showChatRoom(bbno);
			model.addAttribute("room", chatRoomByBoard.get(0));
			log.info(boardId);
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
			
			ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());

			ChatRoom chatrooms = service.findRoomById(chatRoom);
			
			model.addAttribute("roomList", chatRoomList);

			model.addAttribute("room", chatrooms);

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

		ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());

		model.addAttribute("roomList", chatRoomList);
		ChatRoom selectRoom = service.selectByChatRoom(roomId);
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
