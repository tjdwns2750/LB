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

	@Override
	public Member selectUser(String userId) {
		Member member = dao.selectUser(userId);
		return member;
	}

	@Override
	public int updateUser(Member member) {
		// updateForm.html에서 비밀번호를 변경한 경우에 암호화
		if(member.getPw() != null || !member.getPw().equals("")) {
			String encodedPw = passwordEncoder.encode(member.getPw());
			member.setPw(encodedPw);
		}
		
		int result = dao.updateUser(member);
		
		return result;
	}

	@Override
	public void deleteMember(String username) {
		dao.deleteMember(username);
	}
	

}
