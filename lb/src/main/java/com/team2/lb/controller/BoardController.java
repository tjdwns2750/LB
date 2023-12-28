package com.team2.lb.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.team2.lb.service.BoardService;
import com.team2.lb.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	BoardService service;
	
	
	@GetMapping("boardList")
	public String boardList(Model model
			, String type
			, String searchWord) {
		
		ArrayList<Board> boardList = service.selectList(type, searchWord);

		model.addAttribute("boardList", boardList);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
	
		return "board/boardList";
	}
	
	@GetMapping("writeForm")
	public String writeForm() {
		return "board/writeForm";
	}
	
	@PostMapping("write")
	public String writeForm(Board board,
			@AuthenticationPrincipal UserDetails user) {
		
		log.debug("write_board: {}", board);
		
		board.setId(user.getUsername());
		
		int result = service.writeBoard(board);
		
		return "redirect:/board/boardList";
	}
	
	@GetMapping("read")
	public String read(@RequestParam(name = "bno", 
				defaultValue = "0")int bno, Model model) {
		//DB에서 글번호에 일치하는 글정보 가져오기
		Board board = service.readBoard(bno);
		log.debug("board??: {}", board);
		// 리플 목록 가져오기
		//ArrayList<Reply> replyList = rService.replyList(bno);
		
		// model 객체를 이용해 readForm.html에 출력
		model.addAttribute("board", board);
		//model.addAttribute("replyList", replyList);
		return "board/readForm";
			
	}

	@GetMapping("update")
	public String update(
			int bno
			, @AuthenticationPrincipal UserDetails user
			, Model model) {
		
		Board board = service.readBoard(bno);
		if (!board.getId().equals(user.getUsername())) {
			return "redirect:/board/boardList";
		}
		model.addAttribute("board", board);
		
		return "/board/updateForm";
	}
	
	@PostMapping("update")
	public String update(
			Board board
			, @AuthenticationPrincipal UserDetails user) {
		
		log.debug("수정할 글정보 : {}", board);
		
		
		//작성자 아이디 추가
		board.setId(user.getUsername());

		
		int result = service.updateBoard(board);
	
		return "redirect:/board/read?bno=" + board.getBno();
	}
	
	@GetMapping("delete")
	public String delete(int bno
			, @AuthenticationPrincipal UserDetails user) {
		
		Board board = service.readBoard(bno);
		
		if(board.getId().equals(user.getUsername())) {
			//삭제처리
			int result = service.deleteBoard(board);

			}
		
		return "redirect:/board/boardList";
	}
	
	@RequestMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
	    JsonObject jsonObject = new JsonObject();

	    // 내부 경로로 저장
	    String fileRoot = "C:\\Workspace\\git_lab\\lb\\image\\summer\\upload_image\\";

	    String originalFileName = multipartFile.getOriginalFilename();    // 오리지날 파일명
	    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    // 파일 확장자
	    String savedFileName = UUID.randomUUID() + extension;    // 저장될 파일 명

	    File targetFile = new File(fileRoot + savedFileName);
	    try {
	        InputStream fileStream = multipartFile.getInputStream();
	        FileUtils.copyInputStreamToFile(fileStream, targetFile);    // 파일 저장
	        jsonObject.addProperty("url", "/lb/image/summer/upload_image/" + savedFileName); // 수정된 경로
	        jsonObject.addProperty("responseCode", "success");

	    } catch (IOException e) {
	        FileUtils.deleteQuietly(targetFile);    // 저장된 파일 삭제
	        jsonObject.addProperty("responseCode", "error");
	        e.printStackTrace();
	    }
	    return jsonObject.toString();
	}


	
}	

