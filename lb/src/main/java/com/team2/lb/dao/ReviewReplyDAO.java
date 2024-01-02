package com.team2.lb.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.team2.lb.vo.ReviewReply;

@Mapper
public interface ReviewReplyDAO {

	int writeReply(ReviewReply reply);

	int deleteReply(ReviewReply reply);

	ArrayList<ReviewReply> replyList(int bno);

}
