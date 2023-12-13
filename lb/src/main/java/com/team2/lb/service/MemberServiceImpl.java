package com.team2.lb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.MemberDAO;
import com.team2.lb.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberDAO dao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void joinMember(Member member) {
		String encodedPassword = passwordEncoder.encode(member.getPw());
		member.setPw(encodedPassword);
		
		dao.joinMember(member);
		
	}

}
