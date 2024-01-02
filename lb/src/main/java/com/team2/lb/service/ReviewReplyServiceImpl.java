package com.team2.lb.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.lb.dao.ReviewReplyDAO;
import com.team2.lb.vo.ReviewReply;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewReplyServiceImpl implements ReviewReplyService{
	
	@Autowired
	ReviewReplyDAO rDao;

	@Override
	public int writeReply(ReviewReply reply) {
		int result = rDao.writeReply(reply);
		return result;
	}

	@Override
	public int deleteReply(ReviewReply reply) {
		int result = rDao.deleteReply(reply);
		return result;
	}

	@Override
	public ArrayList<ReviewReply> replyList(int bno) {
		ArrayList<ReviewReply> list = rDao.replyList(bno);
		return list;
	}

}
