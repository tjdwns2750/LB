package com.team2.lb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.team2.lb.service.MemberService;
import com.team2.lb.util.FileService;
import com.team2.lb.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
	
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	@Autowired
	MemberService service;
	
	@GetMapping("join")
	public String joinForm() {
		return "member/joinForm";
	}
	
	@GetMapping("idCheck")
	public String idCheckForm() {
		return "member/idCheck";
	}
	
	@PostMapping("idCheck")
	public String idCheck(String searchId, Model model) {
		//DB로부터 가져온다
		boolean result = service.searchId(searchId);
		
		if(result) {
			model.addAttribute("searchId", searchId);
			model.addAttribute("result", "사용할 수 있는 ID입니다.");
		} else {
			model.addAttribute("result", "이미 사용중인 ID입니다.");			
		}
		
		return "member/idCheck";
	}
	
	@PostMapping("join")
	public String join(Member member, MultipartFile upload) {
		log.debug("join_param: {}", member);
		
		if(!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			member.setOriginalfile(upload.getOriginalFilename());
			member.setSavedfile(savedfile);
		}
		service.joinMember(member);
		return "redirect:/";
	}
	
	
	
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@GetMapping("update")
	public String updateForm(@AuthenticationPrincipal UserDetails user
			, Model model) {
		log.debug("update경로_UserDetails 정보: {}", user);
		String userId = user.getUsername();
		Member member = service.selectUser(userId);
		log.debug("DB에서 가져온 Member 정보: {}", member);
		model.addAttribute("member", member);
		
		return "member/updateForm";
	}
	
	@PostMapping("update")
	public String update(@AuthenticationPrincipal UserDetails user
			, Member member, MultipartFile upload) {
		member.setId(user.getUsername());
		
		if(!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			member.setOriginalfile(upload.getOriginalFilename());
			member.setSavedfile(savedfile);
			FileService.deleteFile(uploadPath+"/"+member.getSavedfile());
		}
	
		
		int result = service.updateUser(member);
		log.debug("update 결과: {}", result);
		
		return "redirect:/";
	}
	
	@GetMapping("delete")
	public String delete(@AuthenticationPrincipal UserDetails user) {
		service.deleteMember(user.getUsername());
		return "redirect:/member/logout";
	}
}
