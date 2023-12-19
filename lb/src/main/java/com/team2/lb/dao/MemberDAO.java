package com.team2.lb.dao;

import org.apache.ibatis.annotations.Mapper;

import com.team2.lb.vo.Member;

@Mapper
public interface MemberDAO {

	void joinMember(Member member);

	Member selectUser(String userId);

	int updateUser(Member member);

	void deleteMember(String username);

	Member searchId(String searchId);

}
